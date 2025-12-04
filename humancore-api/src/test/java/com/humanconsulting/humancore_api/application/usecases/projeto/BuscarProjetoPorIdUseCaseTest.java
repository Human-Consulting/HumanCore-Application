package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BuscarProjetoPorIdUseCaseTest {

    private ProjetoRepository projetoRepository;
    private BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase;

    @BeforeEach
    void setUp() {
        projetoRepository = mock(ProjetoRepository.class);
        buscarProjetoPorIdUseCase = new BuscarProjetoPorIdUseCase(projetoRepository);
    }

    @Test
    void execute_ShouldReturnProjeto_WhenProjetoExists() {
        // Arrange
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(1);
        projeto.setDescricao("Projeto Teste");

        when(projetoRepository.findById(1)).thenReturn(Optional.of(projeto));

        // Act
        Projeto resultado = buscarProjetoPorIdUseCase.execute(1);

        // Assert
        assertNotNull(resultado);
        assertEquals(1, resultado.getIdProjeto());
        assertEquals("Projeto Teste", resultado.getDescricao());
        verify(projetoRepository, times(1)).findById(1);
    }

    @Test
    void execute_ShouldThrowException_WhenProjetoDoesNotExist() {
        // Arrange
        when(projetoRepository.findById(99)).thenReturn(Optional.empty());

        // Act & Assert
        EntidadeSemRetornoException exception = assertThrows(
                EntidadeSemRetornoException.class,
                () -> buscarProjetoPorIdUseCase.execute(99)
        );

        assertEquals("Nenhum projeto encontrado.", exception.getMessage());
        verify(projetoRepository, times(1)).findById(99);
    }
}
