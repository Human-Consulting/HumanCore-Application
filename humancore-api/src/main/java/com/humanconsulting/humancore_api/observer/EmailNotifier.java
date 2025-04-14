package com.humanconsulting.humancore_api.observer;
import com.humanconsulting.humancore_api.controller.dto.notificar.NotificacaoEmailDto;
import com.humanconsulting.humancore_api.controller.dto.response.UsuarioResponseDto;
import com.humanconsulting.humancore_api.model.Entrega;
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
    private JavaMailSender emailSender;
    private UsuarioService usuarioService;
    private SprintService sprintService;
    private ProjetoService projetoService;

    @Autowired
    public EmailNotifier(JavaMailSender emailSender, UsuarioService usuarioService, SprintService sprintService, ProjetoService projetoService) {
        this.emailSender = emailSender;
        this.usuarioService = usuarioService;
        this.sprintService = sprintService;
        this.projetoService = projetoService;
    }

    public EmailNotifier(JavaMailSender emailSender) { this.emailSender = emailSender; }

    @Override
    public void update(Entrega entrega) {
        Sprint sprintEntrega = sprintService.buscarPorId(entrega.getFkSprint());
        Projeto projetoEntrega = projetoService.buscarPorId(sprintEntrega.getFkProjeto());
        UsuarioResponseDto responsavelProjeto = usuarioService.buscarPorId(projetoEntrega.getFkResponsavel());
        UsuarioResponseDto responsavelEntrega = usuarioService.buscarPorId(entrega.getFkResponsavel());

        List<String> emails = new ArrayList<>();
        emails.add(responsavelProjeto.getEmail());
        emails.add(responsavelEntrega.getEmail());

        NotificacaoEmailDto notificacao = new NotificacaoEmailDto();
        notificacao.setNomeResponsavel(responsavelProjeto.getNome());
        notificacao.setEmails(emails);
        notificacao.setMensagem(String.format(
                "Impedimento no projeto: %s\n Impedimento registrado na Entrega: %s\n Sprint: %s\n Responsável: %s",
                projetoEntrega.getDescricao(),
                entrega.getDescricao(),
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
