package com.humanconsulting.humancore_api.application.usecases.investimento;

import com.humanconsulting.humancore_api.domain.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DeletarInvestimentoUseCaseTest {

    private InvestimentoRepository investimentoRepository;
    private DeletarInvestimentoUseCase useCase;

    @BeforeEach
    void setUp() {
        investimentoRepository = mock(InvestimentoRepository.class);
        useCase = new DeletarInvestimentoUseCase(investimentoRepository);
    }

    @Test
    void deveDeletarInvestimentoComSucessoQuandoPermissaoValida() {
        // Arrange
        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();
        permissaoDto.setPermissaoEditor("EXCLUIR_INVESTIMENTO");

        Integer idInvestimento = 1;

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("EXCLUIR_INVESTIMENTO", "EXCLUIR_INVESTIMENTO"))
                    .thenAnswer(inv -> null);

            // Act
            useCase.execute(idInvestimento, permissaoDto);

            // Assert
            verify(investimentoRepository, times(1)).deleteById(idInvestimento);
        }
    }

    @Test
    void deveLancarExcecaoQuandoPermissaoInvalida() {
        // Arrange
        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();
        permissaoDto.setPermissaoEditor("CONSULTOR"); // sem permissão

        Integer idInvestimento = 1;

        // Act & Assert
        assertThrows(EntidadeSemPermissaoException.class, () -> useCase.execute(idInvestimento, permissaoDto));

        verify(investimentoRepository, never()).deleteById(anyInt());
    }
}
