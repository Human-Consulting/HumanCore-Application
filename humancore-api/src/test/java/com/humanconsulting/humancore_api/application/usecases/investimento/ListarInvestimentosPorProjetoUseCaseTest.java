package com.humanconsulting.humancore_api.application.usecases.investimento;

import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.domain.utils.PageResult;
import com.humanconsulting.humancore_api.infrastructure.utils.PageResultImpl;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarInvestimentosPorProjetoUseCaseTest {

    private InvestimentoRepository investimentoRepository;
    private ListarInvestimentosPorProjetoUseCase useCase;

    @BeforeEach
    void setUp() {
        investimentoRepository = mock(InvestimentoRepository.class);
        useCase = new ListarInvestimentosPorProjetoUseCase(investimentoRepository);
    }

    @Test
    void deveRetornarInvestimentosQuandoEncontrados() {
        // Arrange
        Investimento investimento = new Investimento();
        investimento.setIdInvestimento(1);
        investimento.setProjeto(mock(Projeto.class));
        investimento.setValor(1000.0);

        PageResult<Investimento> pageResult = new PageResultImpl<>(
                List.of(investimento), 0, 10, 1, 1
        );

        when(investimentoRepository.findAllByProjeto_IdProjeto(1, 0, 10)).thenReturn(pageResult);

        // Act
        PageResult<InvestimentoResponseDto> result = useCase.execute(1, 0, 10);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(1000.0, result.getContent().get(0).getValor());
        verify(investimentoRepository).findAllByProjeto_IdProjeto(1, 0, 10);
    }

    @Test
    void deveLancarExcecaoQuandoNenhumInvestimentoEncontrado() {
        // Arrange
        PageResult<Investimento> pageResult = new PageResultImpl<>(
                Collections.emptyList(), 0, 10, 0, 0
        );

        when(investimentoRepository.findAllByProjeto_IdProjeto(99, 0, 10)).thenReturn(pageResult);

        // Act & Assert
        assertThrows(EntidadeSemRetornoException.class,
                () -> useCase.execute(99, 0, 10));

        verify(investimentoRepository).findAllByProjeto_IdProjeto(99, 0, 10);
    }
}
