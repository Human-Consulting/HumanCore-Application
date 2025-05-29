package com.humanconsulting.humancore_api.observer;
import com.humanconsulting.humancore_api.controller.dto.notificar.NotificacaoEmailDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Sprint;
import com.humanconsulting.humancore_api.model.Usuario;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class EmailNotifier implements Observer {
    private final JavaMailSender emailSender;

    @Override
    public void update(Tarefa tarefa, Sprint sprintEntrega, Projeto projetoEntrega, Usuario tarefaResponsavel, Usuario projetoResponsavel, LoginResponseDto responsavelProjeto, LoginResponseDto responsavelEntrega) {
        List<String> emails = new ArrayList<>();
        emails.add(responsavelProjeto.getEmail());
        if (!responsavelProjeto.getEmail().equals(responsavelEntrega.getEmail()))
            emails.add(responsavelEntrega.getEmail());

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

    @Override
    public void cadastro(Usuario usuario) {
        NotificacaoEmailDto notificacao = new NotificacaoEmailDto();
        notificacao.setNomeResponsavel(usuario.getNome());
        List emailUnico = new ArrayList<>();
        emailUnico.add(usuario.getEmail());
        notificacao.setEmails(emailUnico);
        notificacao.setMensagem(String.format(
                "Olá, %s!\n\nSeu usuário foi adicionado nos projetos de %s como %s na área de %s.\n\nSegue o email cadastrado e a senha gerada para seu primeiro acesso. Por questões de segurança, sugerimos que a senha seja alterada:\n\nEmail: %s\n\nSenha: %s",
                usuario.getNome(),
                usuario.getEmpresa().getNome(),
                usuario.getCargo(),
                usuario.getArea(),
                usuario.getEmail(),
                usuario.getSenha()
        ));

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(usuario.getEmail());
        message.setSubject("Bem vindo à Human Consulting!");
        message.setText(notificacao.getMensagem());

        emailSender.send(message);
    }
}
