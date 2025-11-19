package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuscarEmpresaPorIdUseCaseTest {
    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    BuscarEmpresaPorIdUseCase useCase;

    @Test
    void DadoUmIdValidoQuandoChamadoDeveRetornarEmpresa() {
        Integer id = 1;
        Empresa expected = mock(Empresa.class);

        when(empresaRepository.findById(id)).thenReturn(Optional.of(expected));

        Empresa result = useCase.execute(id);

        assertSame(expected, result);
        verify(empresaRepository, times(1)).findById(id);
    }

    @Test
    void DadoUmIdInvalidoQuandoChamadoDeveRetornarExcecao() {
        Integer id = 2;

        when(empresaRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.execute(id));
        verify(empresaRepository, times(1)).findById(id);
    }
}