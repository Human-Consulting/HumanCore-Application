package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class DeletarEmpresaUseCaseTest {

    @Mock
    private EmpresaRepository empresaRepository;

    @InjectMocks
    private DeletarEmpresaUseCase useCase;

    @Test
    void DadoUmIdValidoEPermissaoCorretaQuandoChamadoDeveDeletarEmpresa() {
        Integer id = 1;

        UsuarioPermissaoDto usuarioPermissaoDto = mock(UsuarioPermissaoDto.class);
        when(usuarioPermissaoDto.getPermissaoEditor()).thenReturn("PERM_EXCLUIR");

        Empresa empresa = mock(Empresa.class);
        when(empresaRepository.findById(id)).thenReturn(Optional.of(empresa));

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("PERM_EXCLUIR", "EXCLUIR_EMPRESA"))
                    .thenAnswer(inv -> null);

            assertDoesNotThrow(() -> useCase.execute(id, usuarioPermissaoDto));

            permMock.verify(() -> ValidarPermissao.execute("PERM_EXCLUIR", "EXCLUIR_EMPRESA"), times(1));

            verify(empresaRepository, times(1)).findById(id);
            verify(empresaRepository, times(1)).deleteById(id);
        }
    }

    @Test
    void DadoUmIdInvalidoEPermissaoCorretaQuandoChamadoDeveLancarExcecaoEntidadeNaoEncontrada() {
        Integer id = 2;

        UsuarioPermissaoDto usuarioPermissaoDto = mock(UsuarioPermissaoDto.class);
        when(usuarioPermissaoDto.getPermissaoEditor()).thenReturn("PERM_EXCLUIR");

        when(empresaRepository.findById(id)).thenReturn(Optional.empty());

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("PERM_EXCLUIR", "EXCLUIR_EMPRESA"))
                    .thenAnswer(inv -> null);

            assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.execute(id, usuarioPermissaoDto));

            permMock.verify(() -> ValidarPermissao.execute("PERM_EXCLUIR", "EXCLUIR_EMPRESA"), times(1));

            verify(empresaRepository, times(1)).findById(id);
            verify(empresaRepository, never()).deleteById(anyInt());
        }
    }

    @Test
    void DadoUmIdValidoEPermissaoIncorretaQuandoChamadoDeveLancarExcecaoEntidadeNaoEncontrada() {
        Integer id = 2;

        UsuarioPermissaoDto usuarioPermissaoDto = mock(UsuarioPermissaoDto.class);
        when(usuarioPermissaoDto.getPermissaoEditor()).thenReturn("PERM_EXCLUIR");

            assertThrows(EntidadeSemPermissaoException.class, () -> useCase.execute(id, usuarioPermissaoDto));

            verify(empresaRepository, never()).deleteById(anyInt());
    }
}