package com.humanconsulting.humancore_api.application.usecases.tarefa;

import com.humanconsulting.humancore_api.application.usecases.projeto.BuscarProjetoPorIdUseCase;
import com.humanconsulting.humancore_api.application.usecases.sprint.BuscarSprintPorIdUseCase;
import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.*;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.infrastructure.configs.RabbitTemplateConfiguration;
import com.humanconsulting.humancore_api.web.dtos.atualizar.tarefa.AtualizarStatusRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.email.EmailUpdateResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.AmqpConnectException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtualizarImpedimentoTarefaUseCaseTest {

    private TarefaRepository tarefaRepository;
    private UsuarioRepository usuarioRepository;
    private RabbitTemplateConfiguration rabbitConfig;
    private RabbitTemplate rabbitTemplate;
    private BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase;
    private BuscarSprintPorIdUseCase buscarSprintPorIdUseCase;
    private TarefaResponseMapper tarefaResponseMapper;
    private AtualizarImpedimentoTarefaUseCase useCase;

    @BeforeEach
    void setUp() {
        tarefaRepository = mock(TarefaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        rabbitConfig = mock(RabbitTemplateConfiguration.class);
        rabbitTemplate = mock(RabbitTemplate.class);
        buscarProjetoPorIdUseCase = mock(BuscarProjetoPorIdUseCase.class);
        buscarSprintPorIdUseCase = mock(BuscarSprintPorIdUseCase.class);
        tarefaResponseMapper = mock(TarefaResponseMapper.class);

        when(rabbitConfig.rabbitTemplate()).thenReturn(rabbitTemplate);

        useCase = new AtualizarImpedimentoTarefaUseCase(
                tarefaRepository, usuarioRepository, rabbitConfig,
                buscarProjetoPorIdUseCase, buscarSprintPorIdUseCase,
                null, tarefaResponseMapper, null
        );
    }

    @Test
    void execute_ShouldUpdateImpedimento_WhenDadosValidos() {
        // Arrange
        Usuario responsavel = new Usuario();
        responsavel.setIdUsuario(10);

        Projeto projeto = new Projeto();
        projeto.setIdProjeto(1);
        projeto.setResponsavel(responsavel);

        Sprint sprint = new Sprint();
        sprint.setIdSprint(2);
        sprint.setProjeto(projeto);

        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(100);
        tarefa.setResponsavel(responsavel);
        tarefa.setSprint(sprint);
        tarefa.setComImpedimento(Boolean.FALSE); // 👈 ESSENCIAL


        AtualizarStatusRequestDto request = new AtualizarStatusRequestDto();
        request.setIdEditor(10);

        when(tarefaRepository.findById(100)).thenReturn(Optional.of(tarefa));
        when(usuarioRepository.findById(10)).thenReturn(Optional.of(responsavel));
        when(buscarProjetoPorIdUseCase.execute(1)).thenReturn(projeto);
        when(buscarSprintPorIdUseCase.execute(2)).thenReturn(new com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto());
        when(tarefaResponseMapper.toResponse(tarefa)).thenReturn(new TarefaResponseDto());

        // Act
        TarefaResponseDto result = useCase.execute(100, request);

        // Assert
        assertNotNull(result);
        verify(tarefaRepository, times(1)).toggleImpedimento(100);
        verify(rabbitTemplate, times(1))
                .convertAndSend(eq("update"), any(EmailUpdateResponseDto.class));

    }

    @Test
    void execute_ShouldThrowException_WhenTarefaNotFound() {
        when(tarefaRepository.findById(999)).thenReturn(Optional.empty());
        AtualizarStatusRequestDto request = new AtualizarStatusRequestDto();
        request.setIdEditor(1);

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> useCase.execute(999, request));
    }

    @Test
    void execute_ShouldThrowException_WhenEditorNotResponsavel() {
        Usuario responsavel = new Usuario();
        responsavel.setIdUsuario(10);

        Projeto projeto = new Projeto();
        projeto.setResponsavel(responsavel);

        Sprint sprint = new Sprint();
        sprint.setProjeto(projeto);

        Tarefa tarefa = new Tarefa();
        tarefa.setResponsavel(responsavel);
        tarefa.setSprint(sprint);

        AtualizarStatusRequestDto request = new AtualizarStatusRequestDto();
        request.setIdEditor(99); // diferente

        when(tarefaRepository.findById(100)).thenReturn(Optional.of(tarefa));
        when(usuarioRepository.findById(10)).thenReturn(Optional.of(responsavel));
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(new Usuario()));
        when(buscarProjetoPorIdUseCase.execute(anyInt())).thenReturn(projeto);
        when(buscarSprintPorIdUseCase.execute(anyInt())).thenReturn(new com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto());

        assertThrows(NullPointerException.class,
                () -> useCase.execute(100, request));
    }

    @Test
    void execute_ShouldThrowRabbitUnavailable_WhenAmqpConnectException() {
        Usuario responsavel = new Usuario();
        responsavel.setIdUsuario(10);

        Projeto projeto = new Projeto();
        projeto.setResponsavel(responsavel);

        Sprint sprint = new Sprint();
        sprint.setProjeto(projeto);

        Tarefa tarefa = new Tarefa();
        tarefa.setResponsavel(responsavel);
        tarefa.setSprint(sprint);

        AtualizarStatusRequestDto request = new AtualizarStatusRequestDto();
        request.setIdEditor(10);

        when(tarefaRepository.findById(100)).thenReturn(Optional.of(tarefa));
        when(usuarioRepository.findById(10)).thenReturn(Optional.of(responsavel));
        when(buscarProjetoPorIdUseCase.execute(anyInt())).thenReturn(projeto);
        when(buscarSprintPorIdUseCase.execute(anyInt())).thenReturn(new com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto());

        doThrow(new AmqpConnectException(null)).when(rabbitTemplate).convertAndSend(Optional.ofNullable(eq("update")), any());

        assertThrows(NullPointerException.class,
                () -> useCase.execute(100, request));
    }

}