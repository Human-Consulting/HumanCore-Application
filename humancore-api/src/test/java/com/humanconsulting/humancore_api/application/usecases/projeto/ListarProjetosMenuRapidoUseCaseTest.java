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

class ListarProjetosMenuRapidoUseCaseTest {

    private ProjetoRepository projetoRepository;
    private ProjetoResponseMapper projetoResponseMapper;
    private ListarProjetosMenuRapidoUseCase listarProjetosMenuRapidoUseCase;

    @BeforeEach
    void setUp() {
        projetoRepository = mock(ProjetoRepository.class);
        projetoResponseMapper = mock(ProjetoResponseMapper.class);
        listarProjetosMenuRapidoUseCase = new ListarProjetosMenuRapidoUseCase(projetoRepository, projetoResponseMapper);
    }

    @Test
    void execute_ShouldReturnProjetos_WhenSemFiltros() {
        // Arrange
        Integer idEmpresa = 1;
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(10);

        ProjetoResponseDto dto = new ProjetoResponseDto();
        dto.setIdProjeto(10);
        dto.setComImpedimento(false);
        dto.setProgresso(50);

        PageResult<Projeto> pageResult = new PageResultImpl<>(List.of(projeto), 0, 10, 1, 1);

        when(projetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa, 0, 10)).thenReturn(pageResult);
        when(projetoResponseMapper.toResponseMenuRapido(projeto)).thenReturn(dto);

        // Act
        PageResult<ProjetoResponseDto> result = listarProjetosMenuRapidoUseCase.execute(idEmpresa, 0, 10, null, false, false);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals(10, result.getContent().get(0).getIdProjeto());
        verify(projetoRepository, times(1)).findAllByEmpresa_IdEmpresa(idEmpresa, 0, 10);
    }

    @Test
    void execute_ShouldReturnOnlyImpedidos_WhenFiltroImpedidos() {
        // Arrange
        Integer idEmpresa = 1;
        Projeto projeto1 = new Projeto();
        Projeto projeto2 = new Projeto();

        ProjetoResponseDto dto1 = new ProjetoResponseDto();
        dto1.setIdProjeto(1);
        dto1.setComImpedimento(true);
        dto1.setProgresso(50);

        ProjetoResponseDto dto2 = new ProjetoResponseDto();
        dto2.setIdProjeto(2);
        dto2.setComImpedimento(false);
        dto2.setProgresso(70);

        PageResult<Projeto> pageResult = new PageResultImpl<>(List.of(projeto1, projeto2), 0, 10, 2, 1);

        when(projetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa, 0, 10)).thenReturn(pageResult);
        when(projetoResponseMapper.toResponseMenuRapido(projeto1)).thenReturn(dto1);
        when(projetoResponseMapper.toResponseMenuRapido(projeto2)).thenReturn(dto2);

        // Act
        PageResult<ProjetoResponseDto> result = listarProjetosMenuRapidoUseCase.execute(idEmpresa, 0, 10, null, true, false);

        // Assert
        assertEquals(1, result.getContent().size());
        assertTrue(result.getContent().get(0).isComImpedimento());
    }

    @Test
    void execute_ShouldReturnOnlyConcluidos_WhenFiltroConcluidos() {
        // Arrange
        Integer idEmpresa = 1;
        Projeto projeto1 = new Projeto();
        Projeto projeto2 = new Projeto();

        ProjetoResponseDto dto1 = new ProjetoResponseDto();
        dto1.setIdProjeto(1);
        dto1.setProgresso(100);

        ProjetoResponseDto dto2 = new ProjetoResponseDto();
        dto2.setIdProjeto(2);
        dto2.setProgresso(50);

        PageResult<Projeto> pageResult = new PageResultImpl<>(List.of(projeto1, projeto2), 0, 10, 2, 1);

        when(projetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa, 0, 10)).thenReturn(pageResult);
        when(projetoResponseMapper.toResponseMenuRapido(projeto1)).thenReturn(dto1);
        when(projetoResponseMapper.toResponseMenuRapido(projeto2)).thenReturn(dto2);

        // Act
        PageResult<ProjetoResponseDto> result = listarProjetosMenuRapidoUseCase.execute(idEmpresa, 0, 10, null, false, true);

        // Assert
        assertEquals(1, result.getContent().size());
        assertEquals(100, result.getContent().get(0).getProgresso());
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
                () -> listarProjetosMenuRapidoUseCase.execute(idEmpresa, 0, 10, null, false, false)
        );

        assertEquals("Nenhuma projeto registrada", exception.getMessage());
    }
}
