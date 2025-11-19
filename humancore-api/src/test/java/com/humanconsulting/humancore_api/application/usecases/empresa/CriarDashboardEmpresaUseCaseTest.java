package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.DashboardEmpresaResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CriarDashboardEmpresaUseCaseTest {
    @Mock
    private EmpresaResponseMapper empresaResponseMapper;

    @Mock
    private BuscarEmpresaPorIdUseCase buscarEmpresaPorIdUseCase;

    @InjectMocks
    CriarDashboardEmpresaUseCase useCase;

    @Test
    void DadoUmaEmpresaExistenteQuandoChamadoDeveCriarDashboard() {
        Integer idEmpresa = 5;
        Empresa empresa = mock(Empresa.class);

        DashboardEmpresaResponseDto dto = mock(DashboardEmpresaResponseDto.class);

        when(buscarEmpresaPorIdUseCase.execute(idEmpresa)).thenReturn(empresa);
        when(empresaResponseMapper.toDashboardResponse(empresa)).thenReturn(dto);

        DashboardEmpresaResponseDto result = useCase.execute(idEmpresa);

        assertSame(dto, result);
        verify(buscarEmpresaPorIdUseCase, times(1)).execute(idEmpresa);
        verify(empresaResponseMapper, times(1)).toDashboardResponse(empresa);
    }

    @Test
    void DadoUmaEmpresaInexistenteQuandoChamadoDeveLancarExcecao() {
        Integer idEmpresa = 99;

        when(buscarEmpresaPorIdUseCase.execute(idEmpresa))
                .thenThrow(new EntidadeNaoEncontradaException("Empresa não encontrada"));

        assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.execute(idEmpresa));

        verify(buscarEmpresaPorIdUseCase, times(1)).execute(idEmpresa);
        verify(empresaResponseMapper, never()).toDashboardResponse(any());
    }
}