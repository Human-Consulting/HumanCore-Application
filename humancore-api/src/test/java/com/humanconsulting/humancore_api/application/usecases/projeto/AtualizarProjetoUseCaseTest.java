package com.humanconsulting.humancore_api.application.usecases.projeto;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.application.usecases.sala.AtualizarSalaUseCase;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.SalaRequestDto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.Set;

public class AtualizarProjetoUseCaseTest {

    @Mock
    private ProjetoRepository projetoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase;

    @Mock
    private AtualizarSalaUseCase atualizarSalaUseCase;

    @Mock
    private ProjetoResponseMapper projetoResponseMapper;

    @InjectMocks
    private AtualizarProjetoUseCase atualizarProjetoUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Explicitly create the use case with dependencies
        atualizarProjetoUseCase = new AtualizarProjetoUseCase(
                projetoRepository,
                usuarioRepository,
                salaRepository,
                buscarProjetoPorIdUseCase,
                projetoResponseMapper,
                atualizarSalaUseCase
        );
    }


    @Test
    void execute_ShouldUpdateProjectSuccessfully_WhenAllConditionsAreMet() {
        // Arrange
        Integer idProjeto = 1;
        String originalUrlImagem = "originalUrl";
        String novoTitulo = "Novo Projeto";

        Projeto projeto = new Projeto();
        projeto.setIdProjeto(idProjeto);
        projeto.setUrlImagem(originalUrlImagem);

        ProjetoAtualizarRequestDto request = new ProjetoAtualizarRequestDto();
        request.setIdEditor(1);
        request.setFkResponsavel(2);
        request.setTitulo(novoTitulo);
        request.setPermissaoEditor("MODIFICAR_PROJETO");
        request.setUrlImagem(""); // importante para cair na lógica de recuperar url original

        Usuario usuarioEditor = new Usuario();
        usuarioEditor.setIdUsuario(1);

        Usuario usuarioResponsavel = new Usuario();
        usuarioResponsavel.setIdUsuario(2);

        Sala sala = new Sala();
        sala.setIdSala(1);
        sala.setUsuarios(Set.of(usuarioEditor, usuarioResponsavel));

        Projeto projetoSalvo = new Projeto();
        projetoSalvo.setIdProjeto(idProjeto);
        projetoSalvo.setTitulo(novoTitulo);
        projetoSalvo.setUrlImagem(originalUrlImagem);
        projetoSalvo.setEmpresa(mock(Empresa.class)); // precisa de empresa para o mapper

        ProjetoResponseDto responseDto = new ProjetoResponseDto();
        responseDto.setIdProjeto(idProjeto);
        responseDto.setTitulo(novoTitulo);

        // Mock dependencies
        when(buscarProjetoPorIdUseCase.execute(idProjeto)).thenReturn(projeto);
        when(projetoRepository.findUrlImagemById(idProjeto)).thenReturn(originalUrlImagem);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(usuarioEditor));
        when(usuarioRepository.findById(2)).thenReturn(Optional.of(usuarioResponsavel));
        when(salaRepository.findByProjeto(projeto)).thenReturn(sala);
        when(projetoRepository.save(any(Projeto.class))).thenReturn(projetoSalvo);
        when(projetoResponseMapper.toResponse(projetoSalvo)).thenReturn(responseDto);

        // Mock static dentro do escopo de execução
        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("MODIFICAR_PROJETO", "MODIFICAR_PROJETO"))
                    .thenAnswer(inv -> null);

            // Act
            ProjetoResponseDto response = atualizarProjetoUseCase.execute(idProjeto, request);

            // Assert
            assertNotNull(response);
            assertEquals(novoTitulo, response.getTitulo());
            verify(projetoRepository, times(1)).save(any(Projeto.class));
            verify(atualizarSalaUseCase, times(1)).execute(eq(1), any(SalaRequestDto.class));
        }
    }



    @Test
    void execute_ShouldThrowException_WhenProjectIsNotFound() {
        // Arrange
        Integer idProjeto = 1;
        ProjetoAtualizarRequestDto request = new ProjetoAtualizarRequestDto();
        request.setIdEditor(1);
        request.setFkResponsavel(2);
        request.setUrlImagem("");
        request.setTitulo("Novo Projeto");
        request.setPermissaoEditor("MODIFICAR_PROJETO");

        when(buscarProjetoPorIdUseCase.execute(idProjeto)).thenReturn(null);

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class, () -> atualizarProjetoUseCase.execute(idProjeto, request));
    }

    @Test
    void execute_ShouldThrowException_WhenEditorUserIsNotFound() {
        // Arrange
        Integer idProjeto = 1;
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(idProjeto);

        ProjetoAtualizarRequestDto request = new ProjetoAtualizarRequestDto();
        request.setIdEditor(999); // Non-existent user
        request.setFkResponsavel(2);
        request.setUrlImagem("");
        request.setTitulo("Novo Projeto");
        request.setPermissaoEditor("MODIFICAR_PROJETO");

        when(buscarProjetoPorIdUseCase.execute(idProjeto)).thenReturn(projeto);
        when(usuarioRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class, () -> atualizarProjetoUseCase.execute(idProjeto, request));
    }

    @Test
    void execute_ShouldThrowException_WhenPermissionIsInvalid() {
        // Arrange
        Integer idProjeto = 1;
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(idProjeto);

        ProjetoAtualizarRequestDto request = new ProjetoAtualizarRequestDto();
        request.setIdEditor(1);
        request.setFkResponsavel(2);
        request.setUrlImagem("");
        request.setTitulo("Novo Projeto");
        request.setPermissaoEditor("INVALID_PERMISSION");

        when(buscarProjetoPorIdUseCase.execute(idProjeto)).thenReturn(projeto);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(new Usuario()));
        when(usuarioRepository.findById(2)).thenReturn(Optional.of(new Usuario()));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> atualizarProjetoUseCase.execute(idProjeto, request));
    }

    @Test
    void execute_ShouldThrowException_WhenResponsibleUserIsNotFound() {
        // Arrange
        Integer idProjeto = 1;
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(idProjeto);

        ProjetoAtualizarRequestDto request = new ProjetoAtualizarRequestDto();
        request.setIdEditor(1);
        request.setFkResponsavel(999); // Non-existent user
        request.setUrlImagem("");
        request.setTitulo("Novo Projeto");
        request.setPermissaoEditor("MODIFICAR_PROJETO");

        when(buscarProjetoPorIdUseCase.execute(idProjeto)).thenReturn(projeto);
        when(usuarioRepository.findById(1)).thenReturn(Optional.of(new Usuario()));
        when(usuarioRepository.findById(999)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeSemPermissaoException.class, () -> atualizarProjetoUseCase.execute(idProjeto, request));
    }

}
