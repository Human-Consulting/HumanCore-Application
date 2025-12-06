package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CadastrarProjetoUseCaseTest {

    private ProjetoRepository projetoRepository;
    private EmpresaRepository empresaRepository;
    private UsuarioRepository usuarioRepository;
    private SalaNotifier salaNotifier;
    private ProjetoResponseMapper projetoResponseMapper;
    private CadastrarProjetoUseCase cadastrarProjetoUseCase;

    @BeforeEach
    void setUp() {
        projetoRepository = mock(ProjetoRepository.class);
        empresaRepository = mock(EmpresaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        salaNotifier = mock(SalaNotifier.class);
        projetoResponseMapper = mock(ProjetoResponseMapper.class);

        cadastrarProjetoUseCase = new CadastrarProjetoUseCase(
                projetoRepository, empresaRepository, usuarioRepository, salaNotifier, projetoResponseMapper
        );
    }

    @Test
    void execute_ShouldCadastrarProjeto_WhenDadosValidos() {
        // Arrange
        ProjetoRequestDto request = new ProjetoRequestDto();
        request.setFkEmpresa(10);
        request.setFkResponsavel(20);
        request.setIdEditor(30);
        request.setDescricao("Novo Projeto");
        request.setPermissaoEditor("ADICIONAR_PROJETO");

        Empresa empresa = mock(Empresa.class);
        Usuario responsavel = new Usuario();
        responsavel.setIdUsuario(20);
        Usuario editor = new Usuario();
        editor.setIdUsuario(30);

        Projeto projetoSalvo = new Projeto();
        projetoSalvo.setIdProjeto(1);
        projetoSalvo.setDescricao("Novo Projeto");

        ProjetoResponseDto responseDto = new ProjetoResponseDto();
        responseDto.setIdProjeto(1);
        responseDto.setTitulo("Novo Projeto");

        when(projetoRepository.existsByEmpresa_IdEmpresaAndDescricao(10, "Novo Projeto")).thenReturn(false);
        when(empresaRepository.findById(10)).thenReturn(Optional.of(empresa));
        when(usuarioRepository.findById(20)).thenReturn(Optional.of(responsavel));
        when(usuarioRepository.findById(30)).thenReturn(Optional.of(editor));
        when(projetoRepository.save(any(Projeto.class))).thenReturn(projetoSalvo);
        when(projetoResponseMapper.toResponse(projetoSalvo)).thenReturn(responseDto);

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("ADICIONAR_PROJETO", "ADICIONAR_PROJETO"))
                    .thenAnswer(inv -> null);

            // Act
            ProjetoResponseDto result = cadastrarProjetoUseCase.execute(request);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.getIdProjeto());
            assertEquals("Novo Projeto", result.getTitulo());
            verify(projetoRepository, times(1)).save(any(Projeto.class));
            verify(salaNotifier, times(1)).onProjetoCriado(projetoSalvo, editor);
        }
    }

    @Test
    void execute_ShouldThrowException_WhenProjetoJaExiste() {
        // Arrange
        ProjetoRequestDto request = new ProjetoRequestDto();
        request.setFkEmpresa(10);
        request.setDescricao("Projeto Existente");
        request.setPermissaoEditor("ADICIONAR_PROJETO");

        when(projetoRepository.existsByEmpresa_IdEmpresaAndDescricao(10, "Projeto Existente")).thenReturn(true);

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("ADICIONAR_PROJETO", "ADICIONAR_PROJETO"))
                    .thenAnswer(inv -> null);

            // Act & Assert
            EntidadeConflitanteException exception = assertThrows(
                    EntidadeConflitanteException.class,
                    () -> cadastrarProjetoUseCase.execute(request)
            );

            assertEquals("Este projeto já foi cadastrado!", exception.getMessage());
            verify(projetoRepository, never()).save(any());
            verify(salaNotifier, never()).onProjetoCriado(any(), any());
        }
    }
}
