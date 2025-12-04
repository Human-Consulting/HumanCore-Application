package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.utils.PageResult;
import com.humanconsulting.humancore_api.infrastructure.utils.PageResultImpl;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarProjetosPorEmpresaUseCaseTest {

    private ProjetoRepository projetoRepository;
    private ProjetoResponseMapper projetoResponseMapper;
    private ListarProjetosPorEmpresaUseCase listarProjetosPorEmpresaUseCase;

    @BeforeEach
    void setUp() {
        projetoRepository = mock(ProjetoRepository.class);
        projetoResponseMapper = mock(ProjetoResponseMapper.class);
        listarProjetosPorEmpresaUseCase = new ListarProjetosPorEmpresaUseCase(projetoRepository, projetoResponseMapper);
    }

    @Test
    void execute_ShouldReturnProjetos_WhenSemFiltroNome() {
        // Arrange
        Integer idEmpresa = 1;
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(10);

        ProjetoResponseDto dto = new ProjetoResponseDto();
        dto.setIdProjeto(10);

        PageResult<Projeto> pageResult = new PageResultImpl<>(List.of(projeto), 0, 10, 1, 1);

        when(projetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa, 0, 10)).thenReturn(pageResult);
        when(projetoResponseMapper.toResponse(projeto)).thenReturn(dto);

        // Act
        PageResult<ProjetoResponseDto> result = listarProjetosPorEmpresaUseCase.execute(idEmpresa, 0, 10, null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(10, result.getContent().get(0).getIdProjeto());
        verify(projetoRepository, times(1)).findAllByEmpresa_IdEmpresa(idEmpresa, 0, 10);
        verify(projetoResponseMapper, times(1)).toResponse(projeto);
    }

    @Test
    void execute_ShouldReturnProjetos_WhenComFiltroNome() {
        // Arrange
        Integer idEmpresa = 1;
        String nome = "Teste";

        Projeto projeto = new Projeto();
        projeto.setIdProjeto(20);

        ProjetoResponseDto dto = new ProjetoResponseDto();
        dto.setIdProjeto(20);

        PageResult<Projeto> pageResult = new PageResultImpl<>(List.of(projeto), 0, 10, 1, 1);

        when(projetoRepository.findAllByEmpresa_IdEmpresaAndNomeContainingIgnoreCase(idEmpresa, 0, 10, nome))
                .thenReturn(pageResult);
        when(projetoResponseMapper.toResponse(projeto)).thenReturn(dto);

        // Act
        PageResult<ProjetoResponseDto> result = listarProjetosPorEmpresaUseCase.execute(idEmpresa, 0, 10, nome);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(20, result.getContent().get(0).getIdProjeto());
        verify(projetoRepository, times(1))
                .findAllByEmpresa_IdEmpresaAndNomeContainingIgnoreCase(idEmpresa, 0, 10, nome);
        verify(projetoResponseMapper, times(1)).toResponse(projeto);
    }

    @Test
    void execute_ShouldThrowException_WhenNenhumProjetoEncontrado() {
        // Arrange
        Integer idEmpresa = 2;
        PageResult<Projeto> emptyPage = new PageResultImpl<>(List.of(), 0, 10, 0, 0);
        when(projetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa, 0, 10)).thenReturn(emptyPage);

        // Act & Assert
        EntidadeSemRetornoException exception = assertThrows(
                EntidadeSemRetornoException.class,
                () -> listarProjetosPorEmpresaUseCase.execute(idEmpresa, 0, 10, null)
        );

        assertEquals("Nenhum projeto encontrado", exception.getMessage());
        verify(projetoRepository, times(1)).findAllByEmpresa_IdEmpresa(idEmpresa, 0, 10);
        verify(projetoResponseMapper, never()).toResponse(any());
    }
}
