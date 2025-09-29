package com.humanconsulting.humancore_api.infrastructure.configs.usecases;

import com.humanconsulting.humancore_api.domain.notifiers.EmailNotifier;
import com.humanconsulting.humancore_api.infrastructure.notifiers.EmailNotifierAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;

@Configuration
public class EmailConfig {

    @Bean
    public EmailNotifier emailNotifier(JavaMailSender javaMailSender) {
        return new EmailNotifierAdapter(javaMailSender);
    }
}
