package com.humanconsulting.humancore_api.application.usecases.checkpoint;

import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.web.dtos.request.CheckpointRequestDto;
import com.humanconsulting.humancore_api.web.mappers.CheckpointMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SincronizarCheckpointsDaTarefaUseCaseTest {
    @Mock
    private CheckpointRepository checkpointRepository;

    @Mock
    private TarefaRepository tarefaRepository;

    @InjectMocks
    SincronizarCheckpointsDaTarefaUseCase useCase;

    @Test
    void DadoUmIdDeTarefaQuandoChamadoDeveSincronizarCheckpoints() {
        Integer idTarefa = 42;

        Checkpoint atual1 = mock(Checkpoint.class);
        when(atual1.getIdCheckpoint()).thenReturn(1);

        Checkpoint atual2 = mock(Checkpoint.class);
        when(atual2.getIdCheckpoint()).thenReturn(2);

        when(checkpointRepository.findAllByTarefa_IdTarefa(idTarefa)).thenReturn(List.of(atual1, atual2));

        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.of(mock(Tarefa.class)));

        CheckpointRequestDto reqExistente = mock(CheckpointRequestDto.class);
        CheckpointRequestDto reqNovo = mock(CheckpointRequestDto.class);

        Checkpoint convertidoExistente = mock(Checkpoint.class);
        when(convertidoExistente.getIdCheckpoint()).thenReturn(2);

        Checkpoint convertidoNovo = mock(Checkpoint.class);
        when(convertidoNovo.getIdCheckpoint()).thenReturn(null);

        try (MockedStatic<CheckpointMapper> mocked = mockStatic(CheckpointMapper.class)) {
            mocked.when(() -> CheckpointMapper.toEntity(reqExistente, null)).thenReturn(convertidoExistente);

            mocked.when(() -> CheckpointMapper.toEntity(reqNovo, null)).thenReturn(convertidoNovo);

            mocked.when(() -> CheckpointMapper.toEntity(eq(reqExistente), any())).thenReturn(convertidoExistente);
            mocked.when(() -> CheckpointMapper.toEntity(eq(reqNovo), any())).thenReturn(convertidoNovo);

            useCase.execute(idTarefa, List.of(reqExistente, reqNovo));
        }

        verify(checkpointRepository, times(1)).save(convertidoExistente);
        verify(checkpointRepository, times(1)).save(convertidoNovo);

        verify(checkpointRepository, times(1)).deleteById(1);
    }

    @Test
    void DadoUmIdDeTarefaInvalidoQuandoChamadoDeveLancarExcecao() {
        Integer idTarefa = 99;

        when(checkpointRepository.findAllByTarefa_IdTarefa(idTarefa))
                .thenThrow(new RuntimeException("erro de banco"));

        CheckpointRequestDto anyReq = mock(CheckpointRequestDto.class);

        assertThrows(RuntimeException.class, () -> useCase.execute(idTarefa, List.of(anyReq)));

        verifyNoInteractions(tarefaRepository);
        verify(checkpointRepository, never()).save(any());
        verify(checkpointRepository, never()).deleteById(anyInt());
    }
}
