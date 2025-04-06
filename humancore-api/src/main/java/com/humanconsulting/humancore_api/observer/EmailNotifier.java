package com.humanconsulting.humancore_api.observer;
import com.humanconsulting.humancore_api.model.Entrega;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailNotifier implements Observer{
    private final JavaMailSender emailSender;

    public EmailNotifier(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    @Override
    public void update(Entrega entrega) {
        if (entrega.getComImpedimento()) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo("diretor@exemplo.com");
            message.setSubject("Impedimento na entrega: " + entrega.getDescricao());
            message.setText("A entrega com ID " + entrega.getIdSprint() + " agora está com impedimento.");

            emailSender.send(message);
        }
    }
}
