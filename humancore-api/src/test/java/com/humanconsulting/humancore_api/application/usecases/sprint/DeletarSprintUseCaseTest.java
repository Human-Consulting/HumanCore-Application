package com.humanconsulting.humancore_api.application.usecases.sprint;

import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeletarSprintUseCaseTest {

    private SprintRepository sprintRepository;
    private DeletarSprintUseCase deletarSprintUseCase;

    @BeforeEach
    void setUp() {
        sprintRepository = mock(SprintRepository.class);
        deletarSprintUseCase = new DeletarSprintUseCase(sprintRepository);
    }

    @Test
    void execute_ShouldDeleteSprint_WhenSprintExistsAndPermissaoValida() {
        // Arrange
        Integer idSprint = 1;
        Sprint sprint = new Sprint();
        sprint.setIdSprint(idSprint);

        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();
        permissaoDto.setPermissaoEditor("EXCLUIR_SPRINT");

        when(sprintRepository.findById(idSprint)).thenReturn(Optional.of(sprint));

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("EXCLUIR_SPRINT", "EXCLUIR_SPRINT"))
                    .thenAnswer(inv -> null);

            // Act
            deletarSprintUseCase.execute(idSprint, permissaoDto);

            // Assert
            verify(sprintRepository, times(1)).deleteById(idSprint);
        }
    }

    @Test
    void execute_ShouldThrowException_WhenSprintNotFound() {
        // Arrange
        Integer idSprint = 99;
        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();
        permissaoDto.setPermissaoEditor("EXCLUIR_SPRINT");

        when(sprintRepository.findById(idSprint)).thenReturn(Optional.empty());

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("EXCLUIR_SPRINT", "EXCLUIR_SPRINT"))
                    .thenAnswer(inv -> null);

            // Act & Assert
            assertThrows(EntidadeNaoEncontradaException.class,
                    () -> deletarSprintUseCase.execute(idSprint, permissaoDto));

            verify(sprintRepository, never()).deleteById(anyInt());
        }
    }

    @Test
    void execute_ShouldThrowException_WhenPermissaoInvalida() {
        // Arrange
        Integer idSprint = 1;
        Sprint sprint = new Sprint();
        sprint.setIdSprint(idSprint);

        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();
        permissaoDto.setPermissaoEditor("SEM_PERMISSAO");

        when(sprintRepository.findById(idSprint)).thenReturn(Optional.of(sprint));

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("SEM_PERMISSAO", "EXCLUIR_SPRINT"))
                    .thenThrow(new SecurityException("Permissão inválida"));

            // Act & Assert
            SecurityException exception = assertThrows(SecurityException.class,
                    () -> deletarSprintUseCase.execute(idSprint, permissaoDto));

            assertEquals("Permissão inválida", exception.getMessage());
            verify(sprintRepository, never()).deleteById(anyInt());
        }
    }
}
