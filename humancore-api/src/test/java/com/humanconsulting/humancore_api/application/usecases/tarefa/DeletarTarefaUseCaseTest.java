package com.humanconsulting.humancore_api.application.usecases.tarefa;

import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeletarTarefaUseCaseTest {

    private TarefaRepository tarefaRepository;
    private DeletarTarefaUseCase deletarTarefaUseCase;

    @BeforeEach
    void setUp() {
        tarefaRepository = mock(TarefaRepository.class);
        deletarTarefaUseCase = new DeletarTarefaUseCase(tarefaRepository);
    }

    @Test
    void execute_ShouldDeleteTarefa_WhenTarefaExistsAndPermissaoValida() {
        // Arrange
        Integer idTarefa = 1;
        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(idTarefa);

        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();
        permissaoDto.setPermissaoEditor("EXCLUIR_TAREFA");

        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.of(tarefa));

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("EXCLUIR_TAREFA", "EXCLUIR_TAREFA"))
                    .thenAnswer(inv -> null);

            // Act
            deletarTarefaUseCase.execute(idTarefa, permissaoDto);

            // Assert
            verify(tarefaRepository, times(1)).deleteById(idTarefa);
        }
    }

    @Test
    void execute_ShouldThrowException_WhenTarefaNotFound() {
        // Arrange
        Integer idTarefa = 99;
        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();
        permissaoDto.setPermissaoEditor("EXCLUIR_TAREFA");

        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.empty());

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("EXCLUIR_TAREFA", "EXCLUIR_TAREFA"))
                    .thenAnswer(inv -> null);

            // Act & Assert
            assertThrows(EntidadeNaoEncontradaException.class,
                    () -> deletarTarefaUseCase.execute(idTarefa, permissaoDto));

            verify(tarefaRepository, never()).deleteById(anyInt());
        }
    }

    @Test
    void execute_ShouldThrowException_WhenPermissaoInvalida() {
        // Arrange
        Integer idTarefa = 1;
        Tarefa tarefa = new Tarefa();
        tarefa.setIdTarefa(idTarefa);

        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();
        permissaoDto.setPermissaoEditor("SEM_PERMISSAO");

        when(tarefaRepository.findById(idTarefa)).thenReturn(Optional.of(tarefa));

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("SEM_PERMISSAO", "EXCLUIR_TAREFA"))
                    .thenThrow(new SecurityException("Permissão inválida"));

            // Act & Assert
            assertThrows(SecurityException.class,
                    () -> deletarTarefaUseCase.execute(idTarefa, permissaoDto));

            verify(tarefaRepository, never()).deleteById(anyInt());
        }
    }
}
