package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.application.usecases.sala.AtualizarSalaUseCase;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.SalaRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.EmpresaMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
class AtualizarEmpresaUseCaseTest {
    @Mock
    private EmpresaRepository empresaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private EmpresaResponseMapper empresaResponseMapper;

    @Mock
    private AtualizarSalaUseCase atualizarSalaUseCase;

    @InjectMocks
    AtualizarEmpresaUseCase useCase;

    @Test
    void DadoUmIdEDtoDeEmpresaValidoQuandoChamadoDeveAtualizarEmpresa() {
        Integer idEmpresa = 1;
        Integer idEditor = 10;
        String urlOriginal = "orig.jpg";

        EmpresaAtualizarRequestDto request = new EmpresaAtualizarRequestDto();
        request.setIdEditor(idEditor);
        request.setNome("Empresa Nova");
        request.setUrlImagem(""); // vazio -> será substituído
        request.setPermissaoEditor("PERM_EDITAR");

        when(empresaRepository.findUrlImagemById(idEmpresa)).thenReturn(urlOriginal);

        Usuario usuarioMock = mock(Usuario.class);
        when(usuarioRepository.findById(idEditor)).thenReturn(Optional.of(usuarioMock));

        Empresa empresaEntity = mock(Empresa.class);
        when(empresaEntity.getNome()).thenReturn("Empresa Nova");
        when(empresaEntity.getUrlImagem()).thenReturn(urlOriginal);
        when(empresaEntity.getIdEmpresa()).thenReturn(idEmpresa);

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class);
             MockedStatic<EmpresaMapper> mapperMock = mockStatic(EmpresaMapper.class)) {

            permMock.when(() -> ValidarPermissao.execute("PERM_EDITAR", "MODIFICAR_EMPRESA"))
                    .thenAnswer(inv -> null);

            mapperMock.when(() -> EmpresaMapper.toEntity(request, idEmpresa)).thenReturn(empresaEntity);

            when(empresaRepository.save(empresaEntity)).thenReturn(empresaEntity);

            Sala salaMock = mock(Sala.class);
            when(salaMock.getIdSala()).thenReturn(55);

            Usuario usuarioSala = mock(Usuario.class);
            when(usuarioSala.getIdUsuario()).thenReturn(100);
            when(salaMock.getUsuarios()).thenReturn(Set.of(usuarioSala));

            when(salaRepository.findByEmpresa(empresaEntity)).thenReturn(salaMock);

            EmpresaResponseDto expectedDto = mock(EmpresaResponseDto.class);
            when(empresaResponseMapper.toResponse(empresaEntity)).thenReturn(expectedDto);

            EmpresaResponseDto result = useCase.execute(idEmpresa, request);

            assertSame(expectedDto, result, "Deve retornar o DTO mapeado");

            assertEquals(urlOriginal, request.getUrlImagem(), "Request.urlImagem deve ser substituída pelo valor original");

            verify(empresaRepository, times(1)).findUrlImagemById(idEmpresa);
            verify(empresaRepository, times(1)).save(empresaEntity);

            ArgumentCaptor<SalaRequestDto> capt = ArgumentCaptor.forClass(SalaRequestDto.class);
            verify(atualizarSalaUseCase, times(1)).execute(eq(55), capt.capture());

            SalaRequestDto sent = capt.getValue();
            assertEquals(empresaEntity.getNome(), sent.getNome());
            assertEquals(empresaEntity.getUrlImagem(), sent.getUrlImagem());
            assertEquals(idEditor, sent.getIdEditor());
        }
    }

    @Test
    void DadoUmIdEDtoDeEmpresaInvalidoQuandoChamadoDeveLancarExcecao() {
        Integer idEmpresa = 2;
        Integer idEditor = 99;

        EmpresaAtualizarRequestDto request = new EmpresaAtualizarRequestDto();
        request.setIdEditor(idEditor);
        request.setNome("Qualquer");
        request.setUrlImagem("img.jpg");
        request.setPermissaoEditor("PERM");

        when(empresaRepository.findUrlImagemById(idEmpresa)).thenReturn("img.jpg");
        when(usuarioRepository.findById(idEditor)).thenReturn(Optional.empty());

        assertThrows(EntidadeNaoEncontradaException.class, () -> useCase.execute(idEmpresa, request));

        verify(empresaRepository, never()).save(any());
        verify(atualizarSalaUseCase, never()).execute(anyInt(), any());
    }
}