package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.projeto.DashboardProjetoResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.mapper.InvestimentoMapper;
import com.humanconsulting.humancore_api.mapper.ProjetoMapper;
import com.humanconsulting.humancore_api.model.*;
import com.humanconsulting.humancore_api.repository.*;
import com.humanconsulting.humancore_api.security.PermissaoValidator;
import com.humanconsulting.humancore_api.utils.ProgressoCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetoServiceTest {
    @Mock ProjetoRepository projetoRepository;
    @Mock UsuarioRepository usuarioRepository;
    @Mock EmpresaRepository empresaRepository;
    @Mock SprintRepository sprintRepository;
    @Mock TarefaRepository tarefaRepository;
    @Mock CheckpointRepository checkpointRepository;
    @Mock DashboardProjetoRepository dashboardProjetoRepository;
    @InjectMocks ProjetoService projetoService;

    @BeforeEach
    void setUp() {
        // Não é necessário inicializar nada, pois @ExtendWith cuida dos mocks
    }

    // Exemplo para passarParaResponse
    @Test
    void passarParaResponse_deveRetornarProjetoResponseDto_quandoDadosValidos() {
        // Arrange
        Projeto projeto = mock(Projeto.class);
        when(sprintRepository.findAll()).thenReturn(List.of(mock(Sprint.class)));
        when(tarefaRepository.existsImpedimentoByProjeto(1)).thenReturn(true);
        List<Checkpoint> checkpoints = List.of(mock(Checkpoint.class));
        when(checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(1)).thenReturn(checkpoints);
        when(ProgressoCalculator.calularProgresso(checkpoints)).thenReturn(0.7);
        ProjetoResponseDto responseDto = mock(ProjetoResponseDto.class);
        try (MockedStatic<ProjetoMapper> projetoMapperMock = mockStatic(ProjetoMapper.class)) {
            projetoMapperMock.when(() -> ProjetoMapper.toDto(projeto, 0.7, true)).thenReturn(responseDto);

            // Act
            ProjetoResponseDto result = projetoService.passarParaResponse(projeto, 2, 1);

            // Assert
            assertEquals(responseDto, result);
        }
    }

    // Exemplo para criarDashboardResponse - caminho feliz
    @Test
    void criarDashboardResponse_deveRetornarDashboardResponseDto_quandoDadosValidos() {
        // Arrange
        Projeto projeto = mock(Projeto.class);
        Usuario usuario = mock(Usuario.class);
        when(projeto.getResponsavel()).thenReturn(usuario);
        when(usuario.getIdUsuario()).thenReturn(2);
        when(usuario.getNome()).thenReturn("Diretor");
        when(projeto.getIdProjeto()).thenReturn(1);
        when(usuarioRepository.findById(2)).thenReturn(Optional.of(usuario));
        when(dashboardProjetoRepository.orcamentoTotal(1)).thenReturn(1000.0);
        when(dashboardProjetoRepository.totalSprints(1)).thenReturn(5);
        when(dashboardProjetoRepository.projetoComImpedimento(1)).thenReturn(false);
        when(checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(1)).thenReturn(List.of());
        try (MockedStatic<ProgressoCalculator> progressoCalculatorMock = mockStatic(ProgressoCalculator.class);
             MockedStatic<ProjetoMapper> projetoMapperMock = mockStatic(ProjetoMapper.class)) {
            progressoCalculatorMock.when(() -> ProgressoCalculator.calularProgresso(anyList())).thenReturn(0.5);
            DashboardProjetoResponseDto dashboardDto = mock(DashboardProjetoResponseDto.class);
            projetoMapperMock.when(() -> ProjetoMapper.toDashboard(any(), anyString(), anyDouble(), anyList(), anyDouble(), anyInt(), anyBoolean(), anyList()))
                    .thenReturn(dashboardDto);

            // Act
            DashboardProjetoResponseDto result = projetoService.criarDashboardResponse(projeto);

            // Assert
            assertEquals(dashboardDto, result);
        }
    }

    // Exemplo para criarDashboardResponse - caminho triste (usuário não encontrado)
    @Test
    void criarDashboardResponse_deveLancarExcecao_quandoUsuarioNaoEncontrado() {
        // Arrange
        Projeto projeto = mock(Projeto.class);
        Usuario usuario = mock(Usuario.class);
        when(projeto.getResponsavel()).thenReturn(usuario);
        when(usuario.getIdUsuario()).thenReturn(2);
        when(usuarioRepository.findById(2)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> projetoService.criarDashboardResponse(projeto));
    }

    // Exemplo para listarTarefasPorArea - feliz

    @Test
    void listarTarefasPorArea_deveRetornarListaDeAreas() {
        // Arrange
        List<Object[]> resultadoBruto = new ArrayList<>();
        resultadoBruto.add(new Object[] { "TI", 3 });
        when(dashboardProjetoRepository.buscarTarefasPorArea(1)).thenReturn(resultadoBruto);

        // Act
        List<Area> result = projetoService.listarTarefasPorArea(1);

        // Assert
        assertEquals(1, result.size());
        assertEquals("TI", result.get(0).getNome());
        assertEquals(3, result.get(0).getValor());
    }


    // Exemplo para listarTarefasPorArea - triste (lista vazia)
    @Test
    void listarTarefasPorArea_deveRetornarListaVazia_quandoNaoExistirTarefas() {
        // Arrange
        when(dashboardProjetoRepository.buscarTarefasPorArea(1)).thenReturn(List.of());

        // Act
        List<Area> result = projetoService.listarTarefasPorArea(1);

        // Assert
        assertTrue(result.isEmpty());
    }

    // Exemplo para listarFinanceiroPorProjeto - feliz
    @Test
    void listarFinanceiroPorProjeto_deveRetornarListaDeInvestimentoResponseDto() {
        // Arrange
        Investimento investimento = mock(Investimento.class);
        when(dashboardProjetoRepository.listarFinanceiroPorEmpresa(1)).thenReturn(List.of(investimento));
        InvestimentoResponseDto responseDto = mock(InvestimentoResponseDto.class);
        try (MockedStatic<InvestimentoMapper> investimentoMapperMock = mockStatic(InvestimentoMapper.class)) {
            investimentoMapperMock.when(() -> InvestimentoMapper.toDto(investimento)).thenReturn(responseDto);

            // Act
            List<InvestimentoResponseDto> result = projetoService.listarFinanceiroPorProjeto(1);

            // Assert
            assertEquals(1, result.size());
            assertEquals(responseDto, result.get(0));
        }
    }

    // Exemplo para listarFinanceiroPorProjeto - triste (lista vazia)
    @Test
    void listarFinanceiroPorProjeto_deveRetornarListaVazia_quandoNaoExistirInvestimentos() {
        // Arrange
        when(dashboardProjetoRepository.listarFinanceiroPorEmpresa(1)).thenReturn(List.of());

        // Act
        List<InvestimentoResponseDto> result = projetoService.listarFinanceiroPorProjeto(1);

        // Assert
        assertTrue(result.isEmpty());
    }
}
