package com.humanconsulting.humancore_api.infrastructure.configs;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String QUEUE_EMAIL_CADASTRO = "email_cadastro_queue";
    public static final String QUEUE_EMAIL_UPDATE = "email_update_queue";
    public static final String QUEUE_EMAIL_CODIGO = "email_codigo_queue";

    public static final String EXCHANGE_NAME = "emailsender.direct.exchange";

    public static final String ROUTING_KEY_CADASTRO = "cadastro";
    public static final String ROUTING_KEY_UPDATE = "update";
    public static final String ROUTING_KEY_CODIGO = "codigo";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME, true, false);
    }

    @Bean
    public Queue emailCadastroQueue() {
        return new Queue(QUEUE_EMAIL_CADASTRO, true);
    }

    @Bean
    public Queue emailUpdateQueue() {
        return new Queue(QUEUE_EMAIL_UPDATE, true);
    }

    @Bean
    public Queue emailCodigoQueue() {
        return new Queue(QUEUE_EMAIL_CODIGO, true);
    }

    @Bean
    public Binding bindingCadastro(Queue emailCadastroQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(emailCadastroQueue).to(directExchange).with(ROUTING_KEY_CADASTRO);
    }

    @Bean
    public Binding bindingUpdate(Queue emailUpdateQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(emailUpdateQueue).to(directExchange).with(ROUTING_KEY_UPDATE);
    }

    @Bean
    public Binding bindingCodigo(Queue emailCodigoQueue, DirectExchange directExchange) {
        return BindingBuilder.bind(emailCodigoQueue).to(directExchange).with(ROUTING_KEY_CODIGO);
    }
}
