package com.humanconsulting.humancore_api.infrastructure.configs;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class TestRabbitProducer {
    private final RabbitTemplate rabbitTemplate;

    public TestRabbitProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
        System.out.println("RabbitTemplate bean criado!");
    }
}
