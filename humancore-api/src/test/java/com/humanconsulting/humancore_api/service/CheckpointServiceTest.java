package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.request.CheckpointRequestDto;
import com.humanconsulting.humancore_api.mapper.CheckpointMapper;
import com.humanconsulting.humancore_api.model.Checkpoint;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.repository.CheckpointRepository;
import com.humanconsulting.humancore_api.repository.TarefaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class CheckpointServiceTest {
    private CheckpointRepository checkpointRepository;
    private TarefaRepository tarefaRepository;
    private CheckpointService checkpointService;

    @BeforeEach
    void setUp() {
        checkpointRepository = mock(CheckpointRepository.class);
        tarefaRepository = mock(TarefaRepository.class);
        checkpointService = new CheckpointService();
        checkpointService.checkpointRepository = checkpointRepository;
        checkpointService.tarefaRepository = tarefaRepository;
    }

    @Test
    void sincronizarCheckpointsDaTarefa_deveSalvarAtualizarEDeletar() {
        // Arrange
        Integer idTarefa = 1;

        Checkpoint atual1 = new Checkpoint();
        atual1.setIdCheckpoint(10);
        Checkpoint atual2 = new Checkpoint();
        atual2.setIdCheckpoint(20);
        List<Checkpoint> atuais = Arrays.asList(atual1, atual2);

        CheckpointRequestDto recebido1 = new CheckpointRequestDto();
        recebido1.setIdCheckpoint(10); // Atualiza
        CheckpointRequestDto recebido2 = new CheckpointRequestDto();
        recebido2.setIdCheckpoint(null); // Novo

        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(idTarefa);

        when(checkpointRepository.findAllByTarefa_IdTarefa(idTarefa)).thenReturn(atuais);
        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.of(tarefa));
        when(checkpointRepository.save(any(Checkpoint.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Checkpoint convertido1 = new Checkpoint();
        convertido1.setIdCheckpoint(10);
        convertido1.setTarefa(tarefa);
        Checkpoint convertido2 = new Checkpoint();
        convertido2.setIdCheckpoint(null);
        convertido2.setTarefa(tarefa);

        mockStatic(CheckpointMapper.class);
        when(CheckpointMapper.toEntity(recebido1, tarefa)).thenReturn(convertido1);
        when(CheckpointMapper.toEntity(recebido2, tarefa)).thenReturn(convertido2);

        // Act
        checkpointService.sincronizarCheckpointsDaTarefa(idTarefa, Arrays.asList(recebido1, recebido2));

        // Assert
        verify(checkpointRepository, times(2)).save(any(Checkpoint.class));
        verify(checkpointRepository, times(1)).delete(atual2);
    }

    @Test
    void sincronizarCheckpointsDaTarefa_deveLancarExcecaoQuandoTarefaNaoEncontrada() {
        // Arrange
        Integer idTarefa = 99;
        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.empty());

        List<CheckpointRequestDto> recebidos = Arrays.asList(new CheckpointRequestDto());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () ->
                checkpointService.sincronizarCheckpointsDaTarefa(idTarefa, recebidos)
        );
    }
}