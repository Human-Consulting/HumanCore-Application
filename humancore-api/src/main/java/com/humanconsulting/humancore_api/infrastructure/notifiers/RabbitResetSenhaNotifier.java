package com.humanconsulting.humancore_api.infrastructure.notifiers;

import com.humanconsulting.humancore_api.domain.notifiers.ResetSenhaNotifier;
import com.humanconsulting.humancore_api.infrastructure.configs.RabbitTemplateConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.AmqpException;

import java.util.Map;

public class RabbitResetSenhaNotifier implements ResetSenhaNotifier {

    private final RabbitTemplateConfiguration rabbitTemplateConfiguration;

    @Value("${broker.queue.reset-senha}")
    private String queueName;

    public RabbitResetSenhaNotifier(RabbitTemplateConfiguration rabbitTemplateConfiguration) {
        this.rabbitTemplateConfiguration = rabbitTemplateConfiguration;
    }

    @Override
    public void sendResetInfo(Map<String, String> resetInfo) {
        try {
            // Envia para a exchange e routing key corretas
            rabbitTemplateConfiguration.rabbitTemplate().convertAndSend(
                    "emailsender.direct.exchange", // exchange
                    "email.reset-senha", // routing key
                    resetInfo);
        } catch (AmqpException e) {
            throw e;
        }
    }
}
