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
class ListarEmpresasUseCaseTest {
    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private EmpresaResponseMapper empresaResponseMapper;

    @InjectMocks
    private ListarEmpresasUseCase useCase;

    @Test
    void DadoEmpresasExistentesQuandoChamadoDeveRetornarAsEmpresas() {
        int page = 0;
        int size = 10;

        Empresa e1 = mock(Empresa.class);
        Empresa e2 = mock(Empresa.class);

        EmpresaResponseDto dto1 = mock(EmpresaResponseDto.class);
        EmpresaResponseDto dto2 = mock(EmpresaResponseDto.class);

        when(empresaResponseMapper.toResponse(e1)).thenReturn(dto1);
        when(empresaResponseMapper.toResponse(e2)).thenReturn(dto2);

        List<Empresa> empresas = List.of(e1, e2);
        PageResult<Empresa> pageFromRepo = new PageResultImpl<>(empresas, page, size, 2L, 1);
        when(empresaRepository.findAll(page, size)).thenReturn(pageFromRepo);


        PageResult<EmpresaResponseDto> result = useCase.execute(page, size, null);

        assertNotNull(result);
        assertEquals(2, result.getContent().size());
        assertSame(dto1, result.getContent().get(0));
        assertSame(dto2, result.getContent().get(1));
        assertEquals(page, result.getPageNumber());
        assertEquals(size, result.getPageSize());
        assertEquals(2L, result.getTotalElements());
        assertEquals(1, result.getTotalPages());

        verify(empresaRepository).findAll(page, size);
        verify(empresaResponseMapper).toResponse(e1);
        verify(empresaResponseMapper).toResponse(e2);
    }

    @Test
    void DadoEmpresasInexistentesQuandoChamadoDeveRetornarExcecao() {

        int page = 0;
        int size = 10;

        PageResult<Empresa> emptyPage = new PageResultImpl<>(List.of(), page, size, 0L, 0);

        when(empresaRepository.findAll(page, size)).thenReturn(emptyPage);

        EntidadeSemRetornoException ex = assertThrows(EntidadeSemRetornoException.class,
                () -> useCase.execute(page, size, null));
        assertNotNull(ex.getMessage());
        verify(empresaRepository).findAll(page, size);
        verifyNoInteractions(empresaResponseMapper);
    }
}
