package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.utils.PageResult;
import com.humanconsulting.humancore_api.infrastructure.utils.PageResultImpl;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ListarEmpresasMenuRapidoUseCaseTest {
    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private EmpresaResponseMapper empresaResponseMapper;

    @InjectMocks
    private ListarEmpresasMenuRapidoUseCase useCase;

    @Test
    void DadoOsDadosDeFiltragemNulosPoremPaginacaoCorretaQuandoChamadoDeveTrazerTodasAsEmpresas() {
        Empresa e1 = mock(Empresa.class);
        Empresa e2 = mock(Empresa.class);

        EmpresaResponseDto dto1 = mock(EmpresaResponseDto.class);
        EmpresaResponseDto dto2 = mock(EmpresaResponseDto.class);

        when(empresaResponseMapper.toResponseMenuRapido(e1)).thenReturn(dto1);
        when(empresaResponseMapper.toResponseMenuRapido(e2)).thenReturn(dto2);

        List<Empresa> empresas = List.of(e1, e2);
        PageResult<Empresa> pageFromRepo = new PageResultImpl<>(empresas, 0, 10, 2L, 1);
        when(empresaRepository.findAll(0, 10)).thenReturn(pageFromRepo);

        PageResult<EmpresaResponseDto> result = useCase.execute(0, 10, null, null, null);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertEquals(0, result.getPageNumber());
        assertEquals(10, result.getPageSize());
        assertEquals(2L, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void DadoOsDadosDeFiltragemValidosQuandoChamadoDeveTrazerApenasUmaEmpresa() {
        int page = 0;
        int size = 1;

        Boolean impedidos = true;
        Boolean concluidos = true;

        Empresa e1 = mock(Empresa.class);
        Empresa e2 = mock(Empresa.class);
        Empresa e3 = mock(Empresa.class);

        EmpresaResponseDto dto1 = mock(EmpresaResponseDto.class); // passa nos dois filtros
        EmpresaResponseDto dto2 = mock(EmpresaResponseDto.class); // falha por impedimento
        EmpresaResponseDto dto3 = mock(EmpresaResponseDto.class); // falha por progresso

        when(empresaResponseMapper.toResponseMenuRapido(e1)).thenReturn(dto1);
        when(empresaResponseMapper.toResponseMenuRapido(e2)).thenReturn(dto2);
        when(empresaResponseMapper.toResponseMenuRapido(e3)).thenReturn(dto3);

        when(dto1.isComImpedimento()).thenReturn(true);
        when(dto1.getProgresso()).thenReturn(Double.valueOf(100));

        when(dto2.isComImpedimento()).thenReturn(false);

        when(dto3.isComImpedimento()).thenReturn(true);
        when(dto3.getProgresso()).thenReturn(Double.valueOf(50));

        List<Empresa> empresas = List.of(e1, e2, e3);

        PageResult<Empresa> pageFromRepo = new PageResultImpl<>(empresas, 0, 10, 3L, 1);

        when(empresaRepository.findAll(page, size)).thenReturn(pageFromRepo);

        PageResult<EmpresaResponseDto> result = useCase.execute(page, size, null, impedidos, concluidos);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertSame(dto1, result.getContent().getFirst());
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(1L, result.getTotalElements());
        assertEquals(1, result.getTotalPages());
    }

    @Test
    void DadoOsDadosDeFiltragemNulosEPaginacaoIncorretaQuandoChamadoDeveLancarExcecao() {
        PageResult<Empresa> emptyPage = new PageResultImpl<>(List.of(), 0, 10, 0L, 0);

        when(empresaRepository.findAll(0, 10)).thenReturn(emptyPage);

        EntidadeSemRetornoException ex = assertThrows(EntidadeSemRetornoException.class,
                () -> useCase.execute(0, 10, null, null, null));
        assertNotNull(ex.getMessage());
    }
}
