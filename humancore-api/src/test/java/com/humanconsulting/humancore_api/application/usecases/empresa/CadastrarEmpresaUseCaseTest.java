package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.EmpresaMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class CadastrarEmpresaUseCaseTest {
    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SalaNotifier salaRepository;

    @Mock
    private EmpresaResponseMapper empresaResponseMapper;

    @InjectMocks
    CadastrarEmpresaUseCase useCase;

    @Test
    void DadoUmDtoValidoQuandoChamadoDeveCadastrarEmpresa() {
        EmpresaRequestDto request = mock(EmpresaRequestDto.class);

        when(request.getPermissaoEditor()).thenReturn("PERM_ADICIONAR");
        when(request.getCnpj()).thenReturn("cnpj");
        when(request.getIdEditor()).thenReturn(7);

        Empresa entidade = mock(Empresa.class);

        Empresa entidadeSalva = mock(Empresa.class);

        Usuario usuario = mock(Usuario.class);

        EmpresaResponseDto resposta = mock(EmpresaResponseDto.class);

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class);
             MockedStatic<EmpresaMapper> mapperMock = mockStatic(EmpresaMapper.class)) {

            permMock.when(() -> ValidarPermissao.execute("PERM_ADICIONAR", "ADICIONAR_EMPRESA"))
                    .thenAnswer(inv -> null);

            mapperMock.when(() -> EmpresaMapper.toEntity(request)).thenReturn(entidade);

            when(empresaRepository.save(entidade)).thenReturn(entidadeSalva);

            when(usuarioRepository.findById(7)).thenReturn(Optional.of(usuario));

            when(empresaResponseMapper.toResponse(entidadeSalva)).thenReturn(resposta);

            EmpresaResponseDto result = useCase.execute(request);

            assertSame(resposta, result);

            permMock.verify(() -> ValidarPermissao.execute("PERM_ADICIONAR", "ADICIONAR_EMPRESA"), times(1));

            verify(empresaRepository, times(1)).existsByCnpj("cnpj");
            verify(empresaRepository, times(1)).save(entidade);

            verify(salaRepository, times(1)).onEmpresaCriada(entidadeSalva, usuario);

            verify(empresaResponseMapper, times(1)).toResponse(entidadeSalva);
        }
    }

    @Test
    void DadoUmDtoInvalidoQuandoChamadoDeveLancarExcecao() {
        EmpresaRequestDto request = mock(EmpresaRequestDto.class);

        when(request.getPermissaoEditor()).thenReturn("PERM");
        when(request.getCnpj()).thenReturn("cnpj2");
        when(request.getIdEditor()).thenReturn(99);

        Empresa entidade = mock(Empresa.class);
        Empresa entidadeSalva = mock(Empresa.class);

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class);
             MockedStatic<EmpresaMapper> mapperMock = mockStatic(EmpresaMapper.class)) {

            permMock.when(() -> ValidarPermissao.execute("PERM", "ADICIONAR_EMPRESA"))
                    .thenAnswer(inv -> null);

            mapperMock.when(() -> EmpresaMapper.toEntity(request)).thenReturn(entidade);

            when(empresaRepository.save(entidade)).thenReturn(entidadeSalva);

            when(usuarioRepository.findById(99)).thenReturn(Optional.empty());

            assertThrows(NoSuchElementException.class, () -> useCase.execute(request));

            verify(empresaRepository, times(1)).save(entidade);

            verify(salaRepository, never()).onEmpresaCriada(any(), any());

            verify(empresaResponseMapper, never()).toResponse(any());
        }
    }
}