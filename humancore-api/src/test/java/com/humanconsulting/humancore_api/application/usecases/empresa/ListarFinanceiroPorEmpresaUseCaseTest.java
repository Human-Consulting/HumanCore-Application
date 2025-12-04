package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.domain.entities.Investimento;
import com.humanconsulting.humancore_api.domain.repositories.DashboardEmpresaRepository;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.web.mappers.InvestimentoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarFinanceiroPorEmpresaUseCaseTest {
    @Mock
    private DashboardEmpresaRepository dashRepository;

    @InjectMocks
    private ListarFinanceiroPorEmpresaUseCase useCase;

    @Test
    void DadoEmpresaExistenteComDadosFinanceirosQuandoChamadoDeveRetornarDadosFinanceiros() {
        Integer idEmpresa = 10;

        Investimento investimento1 = mock(Investimento.class);
        Investimento investimento2 = mock(Investimento.class);

        when(dashRepository.listarFinanceiroPorEmpresa(idEmpresa))
                .thenReturn(Arrays.asList(investimento1, investimento2));

        InvestimentoResponseDto dto1 = mock(InvestimentoResponseDto.class);
        InvestimentoResponseDto dto2 = mock(InvestimentoResponseDto.class);

        try (MockedStatic<InvestimentoMapper> mapperMock1 = mockStatic(InvestimentoMapper.class)) {
            mapperMock1.when(() -> InvestimentoMapper.toDto(investimento1))
                    .thenReturn(dto1);
        }

        try (MockedStatic<InvestimentoMapper> mapperMock2 = mockStatic(InvestimentoMapper.class)) {
            mapperMock2.when(() -> InvestimentoMapper.toDto(investimento1))
                        .thenReturn(dto2);
        }

        List<InvestimentoResponseDto> result = useCase.execute(idEmpresa);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(dashRepository, times(1)).listarFinanceiroPorEmpresa(idEmpresa);
    }

    @Test
    void DadoEmpresaExistenteSemDadosFinanceirosQuandoChamadoDeveRetornarDadosFinanceiros() {
        Integer idEmpresa = 20;

        when(dashRepository.listarFinanceiroPorEmpresa(idEmpresa))
                .thenReturn(Collections.emptyList());

        List<InvestimentoResponseDto> result = useCase.execute(idEmpresa);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(dashRepository).listarFinanceiroPorEmpresa(idEmpresa);
    }
}