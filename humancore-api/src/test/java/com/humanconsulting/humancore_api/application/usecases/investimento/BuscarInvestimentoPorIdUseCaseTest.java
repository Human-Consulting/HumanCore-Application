package com.humanconsulting.humancore_api.application.usecases.investimento;

import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarInvestimentoPorIdUseCaseTest {

    private InvestimentoRepository investimentoRepository;
    private BuscarInvestimentoPorIdUseCase useCase;

    @BeforeEach
    void setUp() {
        investimentoRepository = mock(InvestimentoRepository.class);
        useCase = new BuscarInvestimentoPorIdUseCase(investimentoRepository);
    }

    @Test
    void deveRetornarInvestimentoQuandoEncontrado() {
        // Arrange
        Investimento investimento = new Investimento();
        investimento.setIdInvestimento(1);
        investimento.setProjeto(mock(Projeto.class));
        investimento.setValor(1000.0);

        when(investimentoRepository.findById(1)).thenReturn(Optional.of(investimento));

        // Act
        InvestimentoResponseDto result = useCase.execute(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getIdInvestimento());
        assertEquals(1000.0, result.getValor());
        verify(investimentoRepository).findById(1);
    }

    @Test
    void deveLancarExcecaoQuandoInvestimentoNaoEncontrado() {
        // Arrange
        when(investimentoRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        EntidadeNaoEncontradaException exception = assertThrows(
                EntidadeNaoEncontradaException.class,
                () -> useCase.execute(99)
        );

        assertEquals("InvestimentoEntity não encontrada", exception.getMessage());
        verify(investimentoRepository).findById(99);
    }
}
