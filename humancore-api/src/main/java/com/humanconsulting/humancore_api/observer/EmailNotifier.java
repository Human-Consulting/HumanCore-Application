package com.humanconsulting.humancore_api.observer;
import com.humanconsulting.humancore_api.controller.dto.notificar.NotificacaoEmailDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Sprint;
import com.humanconsulting.humancore_api.service.ProjetoService;
import com.humanconsulting.humancore_api.service.SprintService;
import com.humanconsulting.humancore_api.service.UsuarioService;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmailNotifier implements Observer{
    private final JavaMailSender emailSender;
    private final UsuarioService usuarioService;
    private final SprintService sprintService;
    private final ProjetoService projetoService;

//    @Autowired
    public EmailNotifier(JavaMailSender emailSender, UsuarioService usuarioService, SprintService sprintService, ProjetoService projetoService) {
        this.emailSender = emailSender;
        this.usuarioService = usuarioService;
        this.sprintService = sprintService;
        this.projetoService = projetoService;
    }

//    public EmailNotifier(JavaMailSender emailSender) { this.emailSender = emailSender; }

    @Override
    public void update(Tarefa tarefa) {
        Sprint sprintEntrega = sprintService.buscarPorId(tarefa.getSprint().getIdSprint());
        Projeto projetoEntrega = projetoService.buscarPorId(sprintEntrega.getProjeto().getIdProjeto());
        LoginResponseDto responsavelProjeto = usuarioService.buscarPorId(projetoEntrega.getResponsavel().getIdUsuario());
        LoginResponseDto responsavelEntrega = usuarioService.buscarPorId(tarefa.getResponsavel().getIdUsuario());

        List<String> emails = new ArrayList<>();
        emails.add(responsavelProjeto.getEmail());
        if (!responsavelProjeto.getEmail().equals(responsavelEntrega.getEmail())) emails.add(responsavelEntrega.getEmail());

        NotificacaoEmailDto notificacao = new NotificacaoEmailDto();
        notificacao.setNomeResponsavel(responsavelProjeto.getNome());
        notificacao.setEmails(emails);
        if (tarefa.getComImpedimento()) {
            notificacao.setMensagem(String.format(
                    "Impedimento no projeto: %s\n Impedimento registrado na Tarefa: %s\n Sprint: %s\n Responsável: %s\nComentário anexado: %s",
                    projetoEntrega.getDescricao(),
                    tarefa.getDescricao(),
                    sprintEntrega.getDescricao(),
                    responsavelEntrega.getNome(),
                    tarefa.getComentario()
            ));
        } else {
            notificacao.setMensagem(String.format(
                    "Impedimento finalizado no projeto: %s\n Tarefa: %s\n Sprint: %s\n Responsável: %s\nComentário anexado: %s",
                    projetoEntrega.getDescricao(),
                    tarefa.getDescricao(),
                    sprintEntrega.getDescricao(),
                    responsavelEntrega.getNome(),
                    tarefa.getComentario()
            ));
        }

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emails.toArray(new String[0]));
        message.setSubject("Impedimento no Projeto: " + projetoEntrega.getDescricao());
        message.setText(notificacao.getMensagem());

        emailSender.send(message);
    }
}
