package com.humanconsulting.humancore_api.application.usecases.usuario;

import com.humanconsulting.humancore_api.infrastructure.configs.RabbitTemplateConfiguration;
import com.humanconsulting.humancore_api.infrastructure.exception.RabbitPublishException;
import com.humanconsulting.humancore_api.infrastructure.exception.RabbitUnavailableException;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioEnviarCodigoRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class EnviarCodigoUseCaseTest {

    private RabbitTemplateConfiguration rabbitMQ;
    private RabbitTemplate rabbitTemplate;
    private EnviarCodigoUseCase useCase;

    @BeforeEach
    void setUp() {
        rabbitMQ = mock(RabbitTemplateConfiguration.class);
        rabbitTemplate = mock(RabbitTemplate.class);
        when(rabbitMQ.rabbitTemplate()).thenReturn(rabbitTemplate);

        useCase = new EnviarCodigoUseCase(rabbitMQ);
    }

    @Test
    void DadoUmCódigoQuandoChamadoDeveEnviarCodigoComSucesso() {
        // Arrange
        UsuarioEnviarCodigoRequestDto dto = new UsuarioEnviarCodigoRequestDto();
        dto.setEmail("teste@teste.com");

        // Act
        useCase.execute(dto);

        // Assert
        verify(rabbitTemplate).convertAndSend("codigo", dto);
    }

    @Test
    void DadoRabbitMQIndisponivelQuandoChamadoDeveLancarExcecao() {
        // Arrange
        UsuarioEnviarCodigoRequestDto dto = new UsuarioEnviarCodigoRequestDto();
        dto.setEmail("teste@teste.com");

        doThrow(new AmqpConnectException(new RuntimeException("Falha conexão")))
                .when(rabbitTemplate).convertAndSend(anyString(), Optional.ofNullable(any()));

        // Act & Assert
        assertThrows(RabbitUnavailableException.class, () -> useCase.execute(dto));
    }

    @Test
    void DadoUmaFalhaDeEnvioQuandoChamadoDeveLancarExcecao() {
        // Arrange
        UsuarioEnviarCodigoRequestDto dto = new UsuarioEnviarCodigoRequestDto();
        dto.setEmail("teste@teste.com");

        doThrow(new AmqpException("Falha envio"))
                .when(rabbitTemplate).convertAndSend(anyString(), Optional.ofNullable(any()));

        // Act & Assert
        assertThrows(RabbitPublishException.class, () -> useCase.execute(dto));
    }
}
