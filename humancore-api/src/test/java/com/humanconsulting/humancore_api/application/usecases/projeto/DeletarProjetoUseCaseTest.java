package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DeletarProjetoUseCaseTest {

    private ProjetoRepository projetoRepository;
    private BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase;
    private DeletarProjetoUseCase deletarProjetoUseCase;

    @BeforeEach
    void setUp() {
        projetoRepository = mock(ProjetoRepository.class);
        buscarProjetoPorIdUseCase = mock(BuscarProjetoPorIdUseCase.class);
        deletarProjetoUseCase = new DeletarProjetoUseCase(projetoRepository, buscarProjetoPorIdUseCase);
    }

    @Test
    void execute_ShouldDeleteProjeto_WhenPermissaoValidaAndProjetoExiste() {
        // Arrange
        Integer idProjeto = 1;
        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();
        permissaoDto.setPermissaoEditor("EXCLUIR_PROJETO");

        Projeto projeto = new Projeto();
        projeto.setIdProjeto(idProjeto);

        when(buscarProjetoPorIdUseCase.execute(idProjeto)).thenReturn(projeto);

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("EXCLUIR_PROJETO", "EXCLUIR_PROJETO"))
                    .thenAnswer(inv -> null);

            // Act
            deletarProjetoUseCase.execute(idProjeto, permissaoDto);

            // Assert
            assertNull(projeto.getEmpresa());
            assertNull(projeto.getResponsavel());
            verify(projetoRepository, times(1)).deleteById(idProjeto);
            verify(buscarProjetoPorIdUseCase, times(1)).execute(idProjeto);
        }
    }

    @Test
    void execute_ShouldThrowException_WhenProjetoNaoExiste() {
        // Arrange
        Integer idProjeto = 99;
        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();
        permissaoDto.setPermissaoEditor("EXCLUIR_PROJETO");

        when(buscarProjetoPorIdUseCase.execute(idProjeto))
                .thenThrow(new EntidadeSemRetornoException("Nenhum projeto encontrado."));

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("EXCLUIR_PROJETO", "EXCLUIR_PROJETO"))
                    .thenAnswer(inv -> null);

            // Act & Assert
            EntidadeSemRetornoException exception = assertThrows(
                    EntidadeSemRetornoException.class,
                    () -> deletarProjetoUseCase.execute(idProjeto, permissaoDto)
            );

            assertEquals("Nenhum projeto encontrado.", exception.getMessage());
            verify(projetoRepository, never()).deleteById(anyInt());
        }
    }

    @Test
    void execute_ShouldThrowException_WhenPermissaoInvalida() {
        // Arrange
        Integer idProjeto = 1;
        UsuarioPermissaoDto permissaoDto = new UsuarioPermissaoDto();
        permissaoDto.setPermissaoEditor("SEM_PERMISSAO");

        Projeto projeto = new Projeto();
        projeto.setIdProjeto(idProjeto);

        when(buscarProjetoPorIdUseCase.execute(idProjeto)).thenReturn(projeto);

        try (MockedStatic<ValidarPermissao> permMock = mockStatic(ValidarPermissao.class)) {
            permMock.when(() -> ValidarPermissao.execute("SEM_PERMISSAO", "EXCLUIR_PROJETO"))
                    .thenThrow(new SecurityException("Permissão inválida"));

            // Act & Assert
            SecurityException exception = assertThrows(
                    SecurityException.class,
                    () -> deletarProjetoUseCase.execute(idProjeto, permissaoDto)
            );

            assertEquals("Permissão inválida", exception.getMessage());
            verify(projetoRepository, never()).deleteById(anyInt());
        }
    }
}
