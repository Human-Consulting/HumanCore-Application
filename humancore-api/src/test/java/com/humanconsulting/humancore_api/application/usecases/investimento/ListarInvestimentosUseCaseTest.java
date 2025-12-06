package com.humanconsulting.humancore_api.application.usecases.investimento;

import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarInvestimentosUseCaseTest {

    private InvestimentoRepository investimentoRepository;
    private ListarInvestimentosUseCase useCase;

    @BeforeEach
    void setUp() {
        investimentoRepository = mock(InvestimentoRepository.class);
        useCase = new ListarInvestimentosUseCase(investimentoRepository);
    }

    @Test
    void deveRetornarListaDeInvestimentosQuandoEncontrados() {
        // Arrange
        Investimento investimento = new Investimento();
        investimento.setIdInvestimento(1);
        investimento.setProjeto(mock(Projeto.class));
        investimento.setValor(1000.0);

        when(investimentoRepository.findAll()).thenReturn(List.of(investimento));

        // Act
        List<InvestimentoResponseDto> result = useCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1000.0, result.get(0).getValor());
        verify(investimentoRepository).findAll();
    }

    @Test
    void deveLancarExcecaoQuandoNenhumInvestimentoEncontrado() {
        // Arrange
        when(investimentoRepository.findAll()).thenReturn(Collections.emptyList());

        // Act & Assert
        assertThrows(EntidadeSemRetornoException.class, () -> useCase.execute());

        verify(investimentoRepository).findAll();
    }
}
