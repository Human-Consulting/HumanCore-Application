package com.humanconsulting.humancore_api.application.usecases.tarefa;

import com.humanconsulting.humancore_api.application.usecases.checkpoint.SincronizarCheckpointsDaTarefaUseCase;
import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.atualizar.tarefa.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AtualizarTarefaUseCaseTest {

    private TarefaRepository tarefaRepository;
    private UsuarioRepository usuarioRepository;
    private CheckpointRepository checkpointRepository;
    private SalaNotifier salaNotifier;
    private TarefaResponseMapper tarefaResponseMapper;
    private SincronizarCheckpointsDaTarefaUseCase sincronizarCheckpoints;
    private com.humanconsulting.humancore_api.infrastructure.configs.calendar.GoogleCalendarService googleCalendarService;
    private AtualizarTarefaUseCase useCase;

    @BeforeEach
    void setUp() {
        tarefaRepository = mock(TarefaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        checkpointRepository = mock(CheckpointRepository.class);
        salaNotifier = mock(SalaNotifier.class);
        tarefaResponseMapper = mock(TarefaResponseMapper.class);
        sincronizarCheckpoints = mock(SincronizarCheckpointsDaTarefaUseCase.class);
        googleCalendarService = mock(com.humanconsulting.humancore_api.infrastructure.configs.calendar.GoogleCalendarService.class);

        useCase = new AtualizarTarefaUseCase(
                tarefaRepository, usuarioRepository, checkpointRepository,
                salaNotifier, tarefaResponseMapper, sincronizarCheckpoints,
                googleCalendarService
        );
    }

    @Test
    void execute_ShouldUpdateTarefa_WhenDadosValidos() {
        // Arrange
        Integer idTarefa = 1;
        Usuario editor = new Usuario();
        editor.setIdUsuario(10);

        Usuario responsavel = new Usuario();
        responsavel.setIdUsuario(20);

        Sprint sprint = new Sprint();
        sprint.setIdSprint(5);

        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(idTarefa);
        tarefa.setSprint(sprint);
        tarefa.setResponsavel(responsavel);

        AtualizarGeralRequestDto request = new AtualizarGeralRequestDto();
        request.setIdEditor(10);
        request.setPermissaoEditor("MODIFICAR_TAREFA");
        request.setFkResponsavel(20);

        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.of(tarefa));
        when(usuarioRepository.findById(10)).thenReturn(Optional.of(editor));
        when(usuarioRepository.findById(20)).thenReturn(Optional.of(responsavel));
        when(checkpointRepository.findAllByTarefa_IdTarefa(idTarefa)).thenReturn(List.of(new Checkpoint()));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);
        when(tarefaResponseMapper.toResponse(any(Tarefa.class))).thenReturn(new TarefaResponseDto());

        try (MockedStatic<com.humanconsulting.humancore_api.domain.security.ValidarPermissao> permMock =
                     mockStatic(com.humanconsulting.humancore_api.domain.security.ValidarPermissao.class);
             MockedStatic<com.humanconsulting.humancore_api.domain.utils.ProgressoCalculator> progMock =
                     mockStatic(com.humanconsulting.humancore_api.domain.utils.ProgressoCalculator.class)) {

            permMock.when(() -> com.humanconsulting.humancore_api.domain.security.ValidarPermissao.execute("MODIFICAR_TAREFA", "MODIFICAR_TAREFA"))
                    .thenAnswer(inv -> null);
            progMock.when(() -> com.humanconsulting.humancore_api.domain.utils.ProgressoCalculator.execute(anyList()))
                    .thenReturn(50.0);

            // Act
            try {
                TarefaResponseDto result = useCase.execute(idTarefa, request);

                // Assert
                assertNotNull(result);
                verify(tarefaRepository, times(1)).save(any(Tarefa.class));
                verify(salaNotifier, times(1)).adicionarUsuarioEmSalaProjeto(any(), any(), eq(responsavel));
                verify(sincronizarCheckpoints, times(1)).execute(eq(idTarefa), any());
            } catch (Exception e) {
                fail("Exception should not be thrown: " + e.getMessage());
            }
        }
    }

    @Test
    void execute_ShouldThrowException_WhenTarefaNotFound() {
        when(tarefaRepository.findById(99)).thenReturn(Optional.empty());
        AtualizarGeralRequestDto request = new AtualizarGeralRequestDto();
        request.setIdEditor(10);
        request.setPermissaoEditor("MODIFICAR_TAREFA");

        assertThrows(EntidadeNaoEncontradaException.class,
                () -> useCase.execute(99, request));
    }
}
