package com.humanconsulting.humancore_api.application.usecases.sala;

import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeletarSalaUseCaseTest {

    private SalaRepository salaRepository;
    private DeletarSalaUseCase deletarSalaUseCase;

    @BeforeEach
    void setUp() {
        salaRepository = mock(SalaRepository.class);
        deletarSalaUseCase = new DeletarSalaUseCase(salaRepository);
    }

    @Test
    void execute_ShouldDeleteSala_WhenSalaExistsAndPermissaoValida() {
        // Arrange
        Integer idSala = 1;
        Sala sala = new Sala();
        sala.setIdSala(idSala);

        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();
        permissaoDto.setPermissaoEditor("DELETAR_SALA");

        when(salaRepository.findById(idSala)).thenReturn(Optional.of(sala));

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("DELETAR_SALA", "DELETAR_SALA"))
                    .thenAnswer(inv -> null);

            // Act
            deletarSalaUseCase.execute(idSala, permissaoDto);

            // Assert
            verify(salaRepository, times(1)).deleteById(idSala);
        }
    }

    @Test
    void execute_ShouldThrowException_WhenSalaNotFound() {
        // Arrange
        Integer idSala = 99;
        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();
        permissaoDto.setPermissaoEditor("DELETAR_SALA");

        when(salaRepository.findById(idSala)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class,
                () -> deletarSalaUseCase.execute(idSala, permissaoDto));

        verify(salaRepository, never()).deleteById(anyInt());
    }

    @Test
    void execute_ShouldThrowException_WhenPermissaoInvalida() {
        // Arrange
        Integer idSala = 1;
        Sala sala = new Sala();
        sala.setIdSala(idSala);

        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();
        permissaoDto.setPermissaoEditor("SEM_PERMISSAO");

        when(salaRepository.findById(idSala)).thenReturn(Optional.of(sala));

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("SEM_PERMISSAO", "DELETAR_SALA"))
                    .thenThrow(new SecurityException("Permissão inválida"));

            // Act & Assert
            SecurityException exception = assertThrows(SecurityException.class,
                    () -> deletarSalaUseCase.execute(idSala, permissaoDto));

            assertEquals("Permissão inválida", exception.getMessage());
            verify(salaRepository, never()).deleteById(anyInt());
        }
    }
}
