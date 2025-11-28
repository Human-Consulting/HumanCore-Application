package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.KpiProjetoResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.web.mappers.ProjetoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarProjetosKpisUseCaseTest {

    private ProjetoRepository projetoRepository;
    private ProjetoResponseMapper projetoResponseMapper;
    private ProjetoMapper projetoMapper;
    private ListarProjetosKpisUseCase listarProjetosKpisUseCase;

    @BeforeEach
    void setUp() {
        projetoRepository = mock(ProjetoRepository.class);
        projetoResponseMapper = mock(ProjetoResponseMapper.class);
        projetoMapper = new ProjetoMapper(); // pode ser instância real se não tiver lógica complexa
        listarProjetosKpisUseCase = new ListarProjetosKpisUseCase(projetoRepository, projetoResponseMapper, projetoMapper);
    }

    @Test
    void execute_ShouldReturnKpiDto_WhenProjetosExistem() {
        // Arrange
        Integer idEmpresa = 1;

        Projeto projeto1 = new Projeto(); // impedido
        Projeto projeto2 = new Projeto(); // finalizado
        Projeto projeto3 = new Projeto(); // andamento

        ProjetoResponseDto dto1 = new ProjetoResponseDto();
        dto1.setComImpedimento(true);
        dto1.setProgresso(50);

        ProjetoResponseDto dto2 = new ProjetoResponseDto();
        dto2.setComImpedimento(false);
        dto2.setProgresso(100);

        ProjetoResponseDto dto3 = new ProjetoResponseDto();
        dto3.setComImpedimento(false);
        dto3.setProgresso(70);

        when(projetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa))
                .thenReturn(List.of(projeto1, projeto2, projeto3));

        when(projetoResponseMapper.toResponseKpi(projeto1)).thenReturn(dto1);
        when(projetoResponseMapper.toResponseKpi(projeto2)).thenReturn(dto2);
        when(projetoResponseMapper.toResponseKpi(projeto3)).thenReturn(dto3);

        // Act
        KpiProjetoResponseDto result = listarProjetosKpisUseCase.execute(idEmpresa);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.getTotalAndamento()); // dto1 e dto3 têm progresso < 100

        verify(projetoRepository, times(1)).findAllByEmpresa_IdEmpresa(idEmpresa);
        verify(projetoResponseMapper, times(3)).toResponseKpi(any(Projeto.class));
    }

    @Test
    void execute_ShouldThrowException_WhenNenhumProjetoEncontrado() {
        // Arrange
        Integer idEmpresa = 2;
        when(projetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa)).thenReturn(List.of());

        // Act & Assert
        EntidadeSemRetornoException exception = assertThrows(
                EntidadeSemRetornoException.class,
                () -> listarProjetosKpisUseCase.execute(idEmpresa)
        );

        assertEquals("Nenhuma projeto registrada", exception.getMessage());
        verify(projetoRepository, times(1)).findAllByEmpresa_IdEmpresa(idEmpresa);
        verify(projetoResponseMapper, never()).toResponseKpi(any());
    }
}
