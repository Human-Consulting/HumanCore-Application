package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.KpiEmpresaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.EmpresaMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarEmpresasKpisUseCaseTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private EmpresaResponseMapper empresaResponseMapper;

    @InjectMocks
    private ListarEmpresasKpisUseCase useCase;

    @Test
    void DadoEmpresasExistentesQuandoChamadoDeveRetornarOsKpisDasEmpresas() {
        Empresa empresa1 = mock(Empresa.class);
        Empresa empresa2 = mock(Empresa.class);

        EmpresaResponseDto dto1 = mock(EmpresaResponseDto.class);
        EmpresaResponseDto dto2 = mock(EmpresaResponseDto.class);

        when(empresaRepository.findAll()).thenReturn(List.of(empresa1, empresa2));

        when(empresaResponseMapper.toResponseKpi(empresa1)).thenReturn(dto1);
        when(empresaResponseMapper.toResponseKpi(empresa2)).thenReturn(dto2);

        when(dto1.isComImpedimento()).thenReturn(true);
        when(dto1.getProgresso()).thenReturn(Double.valueOf(50));

        when(dto2.isComImpedimento()).thenReturn(false);
        when(dto2.getProgresso()).thenReturn(Double.valueOf(100));

        List<EmpresaResponseDto> expectedImpedidos = new ArrayList<>();
        expectedImpedidos.add(dto1);

        List<EmpresaResponseDto> expectedFinalizadas = new ArrayList<>();
        expectedFinalizadas.add(dto2);

        int expectedTotalAndamento = 1;

        KpiEmpresaResponseDto kpiDto = mock(KpiEmpresaResponseDto.class);

        try (MockedStatic<EmpresaMapper> mapperMock = mockStatic(EmpresaMapper.class)) {
            mapperMock.when(() -> EmpresaMapper.toKpiDto(expectedImpedidos, expectedFinalizadas, expectedTotalAndamento))
                    .thenReturn(kpiDto);

            KpiEmpresaResponseDto result = useCase.execute();

            assertSame(kpiDto, result);

            verify(empresaRepository, times(1)).findAll();

            verify(empresaResponseMapper, times(1)).toResponseKpi(empresa1);
            verify(empresaResponseMapper, times(1)).toResponseKpi(empresa2);

            mapperMock.verify(() -> EmpresaMapper.toKpiDto(expectedImpedidos, expectedFinalizadas, expectedTotalAndamento), times(1));
        }
    }

    @Test
    void DadoEmpresasInexistentesQuandoChamadoDeveLancarExcecao() {
        when(empresaRepository.findAll()).thenReturn(List.of());

        try (MockedStatic<EmpresaMapper> mapperMock = mockStatic(EmpresaMapper.class)) {
            assertThrows(EntidadeSemRetornoException.class, () -> useCase.execute());

            verify(empresaRepository, times(1)).findAll();

            verify(empresaResponseMapper, never()).toResponseKpi(any());

            mapperMock.verifyNoInteractions();
        }
    }
}
