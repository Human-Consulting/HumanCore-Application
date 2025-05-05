package com.humanconsulting.humancore_api.observer;
import com.humanconsulting.humancore_api.controller.dto.notificar.NotificacaoEmailDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Sprint;
import com.humanconsulting.humancore_api.service.ProjetoService;
import com.humanconsulting.humancore_api.service.SprintService;
import com.humanconsulting.humancore_api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
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

    public EmailNotifier(JavaMailSender emailSender, UsuarioService usuarioService, SprintService sprintService, ProjetoService projetoService) {
        this.emailSender = emailSender;
        this.usuarioService = usuarioService;
        this.sprintService = sprintService;
        this.projetoService = projetoService;
    }

    @Override
    public void update(Tarefa tarefa) {
        Sprint sprintEntrega = sprintService.buscarPorId(tarefa.getFkSprint());
        Projeto projetoEntrega = projetoService.buscarPorId(sprintEntrega.getFkProjeto());
        LoginResponseDto responsavelProjeto = usuarioService.buscarPorId(projetoEntrega.getFkResponsavel());
        LoginResponseDto responsavelEntrega = usuarioService.buscarPorId(tarefa.getFkResponsavel());

        List<String> emails = new ArrayList<>();
        emails.add(responsavelProjeto.getEmail());
        emails.add(responsavelEntrega.getEmail());

        NotificacaoEmailDto notificacao = new NotificacaoEmailDto();
        notificacao.setNomeResponsavel(responsavelProjeto.getNome());
        notificacao.setEmails(emails);
        notificacao.setMensagem(String.format(
                "Impedimento no projeto: %s\n Impedimento registrado na Entrega: %s\n Sprint: %s\n Responsável: %s",
                projetoEntrega.getDescricao(),
                tarefa.getDescricao(),
                sprintEntrega.getDescricao(),
                responsavelEntrega.getNome()
        ));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emails.toArray(new String[0]));
        message.setSubject("Impedimento no Projeto: " + projetoEntrega.getDescricao());
        message.setText(notificacao.getMensagem());

        emailSender.send(message);
    }
}
