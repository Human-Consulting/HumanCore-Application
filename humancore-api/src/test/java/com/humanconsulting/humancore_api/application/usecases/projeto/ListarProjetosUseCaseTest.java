package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarProjetosUseCaseTest {

    private ProjetoRepository projetoRepository;
    private ProjetoResponseMapper projetoResponseMapper;
    private ListarProjetosUseCase listarProjetosUseCase;

    @BeforeEach
    void setUp() {
        projetoRepository = mock(ProjetoRepository.class);
        projetoResponseMapper = mock(ProjetoResponseMapper.class);
        listarProjetosUseCase = new ListarProjetosUseCase(projetoRepository, projetoResponseMapper);
    }

    @Test
    void execute_ShouldReturnProjetos_WhenProjetosExistem() {
        // Arrange
        Projeto projeto1 = new Projeto();
        projeto1.setIdProjeto(1);
        Projeto projeto2 = new Projeto();
        projeto2.setIdProjeto(2);

        ProjetoResponseDto dto1 = new ProjetoResponseDto();
        dto1.setIdProjeto(1);
        ProjetoResponseDto dto2 = new ProjetoResponseDto();
        dto2.setIdProjeto(2);

        when(projetoRepository.findAll()).thenReturn(List.of(projeto1, projeto2));
        when(projetoResponseMapper.toResponse(projeto1)).thenReturn(dto1);
        when(projetoResponseMapper.toResponse(projeto2)).thenReturn(dto2);

        // Act
        List<ProjetoResponseDto> result = listarProjetosUseCase.execute();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getIdProjeto());
        assertEquals(2, result.get(1).getIdProjeto());

        verify(projetoRepository, times(1)).findAll();
        verify(projetoResponseMapper, times(2)).toResponse(any(Projeto.class));
    }

    @Test
    void execute_ShouldThrowException_WhenNenhumProjetoEncontrado() {
        // Arrange
        when(projetoRepository.findAll()).thenReturn(List.of());

        // Act & Assert
        EntidadeSemRetornoException exception = assertThrows(
                EntidadeSemRetornoException.class,
                () -> listarProjetosUseCase.execute()
        );

        assertEquals("Nenhum projeto registrado.", exception.getMessage());
        verify(projetoRepository, times(1)).findAll();
        verify(projetoResponseMapper, never()).toResponse(any());
    }
}
