package com.humanconsulting.humancore_api.application.usecases.tarefa;

import com.humanconsulting.humancore_api.application.usecases.checkpoint.SincronizarCheckpointsDaTarefaUseCase;
import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.*;
import com.humanconsulting.humancore_api.domain.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.request.CheckpointRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.TarefaRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastrarTarefaUseCaseTest {

    private TarefaRepository tarefaRepository;
    private SprintRepository sprintRepository;
    private UsuarioRepository usuarioRepository;
    private SalaNotifier salaNotifier;
    private TarefaResponseMapper tarefaResponseMapper;
    private SincronizarCheckpointsDaTarefaUseCase sincronizarCheckpoints;
    private CadastrarTarefaUseCase useCase;

    @BeforeEach
    void setUp() {
        tarefaRepository = mock(TarefaRepository.class);
        sprintRepository = mock(SprintRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        salaNotifier = mock(SalaNotifier.class);
        tarefaResponseMapper = mock(TarefaResponseMapper.class);
        sincronizarCheckpoints = mock(SincronizarCheckpointsDaTarefaUseCase.class);

        useCase = new CadastrarTarefaUseCase(
                tarefaRepository, sprintRepository, usuarioRepository,
                salaNotifier, tarefaResponseMapper, sincronizarCheckpoints
        );
    }

    @Test
    void execute_ShouldCadastrarTarefa_WhenDadosValidos() {
        // Arrange
        Sprint sprint = new Sprint();
        sprint.setIdSprint(1);
        sprint.setDtInicio(LocalDate.of(2025, 1, 1));
        sprint.setDtFim(LocalDate.of(2025, 1, 10));
        sprint.setProjeto(new Projeto());

        Usuario responsavel = new Usuario();
        responsavel.setIdUsuario(20);

        CheckpointRequestDto checkpointDto = mock(CheckpointRequestDto.class);

        TarefaRequestDto request = new TarefaRequestDto();
        request.setPermissaoEditor("ADICIONAR_TAREFA");
        request.setFkSprint(1);
        request.setFkResponsavel(20);
        request.setDtInicio(LocalDate.of(2025, 1, 2));
        request.setDtFim(LocalDate.of(2025, 1, 5));
        request.setCheckpoints(List.of(checkpointDto));

        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(100);
        tarefa.setSprint(sprint);
        tarefa.setResponsavel(responsavel);

        TarefaResponseDto responseDto = new TarefaResponseDto();
        responseDto.setIdTarefa(100);

        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));
        when(usuarioRepository.findById(20)).thenReturn(Optional.of(responsavel));
        when(tarefaRepository.save(any(Tarefa.class))).thenReturn(tarefa);
        when(tarefaResponseMapper.toResponse(tarefa)).thenReturn(responseDto);

        try (MockedStatic<com.humanconsulting.humancore_api.domain.security.ValidarPermissao> permMock =
                     mockStatic(com.humanconsulting.humancore_api.domain.security.ValidarPermissao.class)) {
            permMock.when(() -> com.humanconsulting.humancore_api.domain.security.ValidarPermissao.execute("ADICIONAR_TAREFA", "ADICIONAR_TAREFA"))
                    .thenAnswer(inv -> null);

            // Act
            TarefaResponseDto result = useCase.execute(request);

            // Assert
            assertNotNull(result);
            assertEquals(100, result.getIdTarefa());
            verify(tarefaRepository, times(1)).save(any(Tarefa.class));
            verify(sincronizarCheckpoints, times(1)).execute(eq(100), eq(List.of(checkpointDto)));
            verify(salaNotifier, times(1)).adicionarUsuarioEmSalaProjeto(eq(tarefa), eq(sprint.getProjeto()), eq(responsavel));
        }
    }

    @Test
    void execute_ShouldThrowException_WhenDatasInvalidas() {
        Sprint sprint = new Sprint();
        sprint.setDtInicio(LocalDate.of(2025, 1, 1));
        sprint.setDtFim(LocalDate.of(2025, 1, 10));

        TarefaRequestDto request = new TarefaRequestDto();
        request.setPermissaoEditor("ADICIONAR_TAREFA");
        request.setFkSprint(1);
        request.setDtInicio(LocalDate.of(2025, 1, 10));
        request.setDtFim(LocalDate.of(2025, 1, 10));

        when(sprintRepository.findById(1)).thenReturn(Optional.of(sprint));

        try (MockedStatic<com.humanconsulting.humancore_api.domain.security.ValidarPermissao> permMock =
                     mockStatic(com.humanconsulting.humancore_api.domain.security.ValidarPermissao.class)) {
            permMock.when(() -> com.humanconsulting.humancore_api.domain.security.ValidarPermissao.execute("ADICIONAR_TAREFA", "ADICIONAR_TAREFA"))
                    .thenAnswer(inv -> null);

            assertThrows(EntidadeConflitanteException.class,
                    () -> useCase.execute(request));
        }
    }
}
