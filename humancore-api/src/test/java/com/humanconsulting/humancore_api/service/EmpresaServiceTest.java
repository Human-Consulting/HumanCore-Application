package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.velho.controller.dto.response.empresa.DashboardEmpresaResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.velho.mapper.EmpresaMapper;
import com.humanconsulting.humancore_api.velho.mapper.InvestimentoMapper;
import com.humanconsulting.humancore_api.velho.model.Area;
import com.humanconsulting.humancore_api.velho.model.Checkpoint;
import com.humanconsulting.humancore_api.velho.model.Empresa;
import com.humanconsulting.humancore_api.velho.model.Investimento;
import com.humanconsulting.humancore_api.velho.repository.CheckpointRepository;
import com.humanconsulting.humancore_api.velho.repository.DashboardEmpresaRepository;
import com.humanconsulting.humancore_api.velho.repository.EmpresaRepository;
import com.humanconsulting.humancore_api.velho.repository.UsuarioRepository;
import com.humanconsulting.humancore_api.velho.service.EmpresaService;
import com.humanconsulting.humancore_api.velho.utils.ProgressoCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mockStatic;

public class EmpresaServiceTest {
    private EmpresaService empresaService;
    private EmpresaRepository empresaRepository;
    private CheckpointRepository checkpointRepository;
    private DashboardEmpresaRepository dashRepository;
    private UsuarioRepository usuarioRepository;

    @BeforeEach
    void setUp() {
        empresaRepository = mock(EmpresaRepository.class);
        checkpointRepository = mock(CheckpointRepository.class);
        dashRepository = mock(DashboardEmpresaRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        empresaService = new EmpresaService();
        empresaService.empresaRepository = empresaRepository;
        empresaService.checkpointRepository = checkpointRepository;
        empresaService.dashRepository = dashRepository;
        empresaService.usuarioRepository = usuarioRepository;
    }

    @Test
    void passarParaResponse_deveMontarDtoCorretamente() {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(1);

        when(usuarioRepository.findDiretorByEmpresaId(1)).thenReturn(Optional.of("Diretor Teste"));
        when(dashRepository.empresaComImpedimento(1)).thenReturn(true);
        when(dashRepository.orcamentoTotal(1)).thenReturn(1000.0);
        List<Checkpoint> checkpoints = Collections.singletonList(new Checkpoint());
        when(checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(1)).thenReturn(checkpoints);

        try (MockedStatic<ProgressoCalculator> progressoMock = mockStatic(ProgressoCalculator.class);
             MockedStatic<EmpresaMapper> mapperMock = mockStatic(EmpresaMapper.class)) {
            progressoMock.when(() -> ProgressoCalculator.calularProgresso(checkpoints)).thenReturn(0.75);

            EmpresaResponseDto dtoEsperado = new EmpresaResponseDto();
            mapperMock.when(() -> EmpresaMapper.toDto(empresa, "Diretor Teste", true, 0.75, 1000.0))
                    .thenReturn(dtoEsperado);

            // Act
            EmpresaResponseDto dto = empresaService.passarParaResponse(empresa);

            // Assert
            assertSame(dtoEsperado, dto);
            verify(usuarioRepository).findDiretorByEmpresaId(1);
            verify(dashRepository).empresaComImpedimento(1);
            verify(dashRepository).orcamentoTotal(1);
            verify(checkpointRepository).findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(1);
        }
    }

    @Test
    void passarParaResponse_deveRetornarDiretorNullQuandoNaoEncontrado() {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(2);

        when(usuarioRepository.findDiretorByEmpresaId(2)).thenReturn(Optional.empty());
        when(dashRepository.empresaComImpedimento(2)).thenReturn(false);
        when(dashRepository.orcamentoTotal(2)).thenReturn(500.0);
        List<Checkpoint> checkpoints = Collections.emptyList();
        when(checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(2)).thenReturn(checkpoints);

        try (MockedStatic<ProgressoCalculator> progressoMock = mockStatic(ProgressoCalculator.class);
             MockedStatic<EmpresaMapper> mapperMock = mockStatic(EmpresaMapper.class)) {
            progressoMock.when(() -> ProgressoCalculator.calularProgresso(checkpoints)).thenReturn(0.0);

            EmpresaResponseDto dtoEsperado = new EmpresaResponseDto();
            mapperMock.when(() -> EmpresaMapper.toDto(empresa, null, false, 0.0, 500.0))
                    .thenReturn(dtoEsperado);

            // Act
            EmpresaResponseDto dto = empresaService.passarParaResponse(empresa);

            // Assert
            assertSame(dtoEsperado, dto);
        }
    }

    @Test
    void criarDashboardResponse_deveMontarDashboardCorretamente() {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(1);

        EmpresaService empresaServiceSpy = spy(empresaService);

        when(usuarioRepository.findDiretorByEmpresaId(1)).thenReturn(Optional.of("Diretor Teste"));
        List<Area> areas = Arrays.asList(new Area("TI", 5));
        doReturn(areas).when(empresaServiceSpy).listarTarefasPorArea(1);
        when(dashRepository.orcamentoTotal(1)).thenReturn(2000.0);
        when(dashRepository.totalProjetos(1)).thenReturn(3);
        when(dashRepository.empresaComImpedimento(1)).thenReturn(false);
        List<InvestimentoResponseDto> investimentos = Collections.singletonList(new InvestimentoResponseDto());
        doReturn(investimentos).when(empresaServiceSpy).listarFinanceiroPorEmpresa(1);
        List<Checkpoint> checkpoints = Collections.singletonList(new Checkpoint());
        when(checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(1)).thenReturn(checkpoints);

        try (MockedStatic<ProgressoCalculator> progressoMock = mockStatic(ProgressoCalculator.class);
             MockedStatic<EmpresaMapper> mapperMock = mockStatic(EmpresaMapper.class)) {
            progressoMock.when(() -> ProgressoCalculator.calularProgresso(checkpoints)).thenReturn(0.8);

            DashboardEmpresaResponseDto dashboardEsperado = new DashboardEmpresaResponseDto();
            mapperMock.when(() -> EmpresaMapper.toDashboard(
                    empresa, "Diretor Teste", 0.8, areas, 2000.0, 3, false, investimentos
            )).thenReturn(dashboardEsperado);

            // Act
            DashboardEmpresaResponseDto dashboard = empresaServiceSpy.criarDashboardResponse(empresa);

            // Assert
            assertSame(dashboardEsperado, dashboard);
            verify(usuarioRepository).findDiretorByEmpresaId(1);
            verify(dashRepository).orcamentoTotal(1);
            verify(dashRepository).totalProjetos(1);
            verify(dashRepository).empresaComImpedimento(1);
            verify(checkpointRepository).findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(1);
        }
    }

    @Test
    void criarDashboardResponse_deveLancarExcecaoQuandoDiretorNaoEncontrado() {
        // Arrange
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(99);

        when(usuarioRepository.findDiretorByEmpresaId(99)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NoSuchElementException.class, () -> empresaService.criarDashboardResponse(empresa));
        verify(usuarioRepository).findDiretorByEmpresaId(99);
    }

    @Test
    void listarTarefasPorArea_deveRetornarAreasCorretamente() {
        // Arrange
        Integer idEmpresa = 1;
        Object[] area1 = new Object[]{"TI", 5};
        Object[] area2 = new Object[]{"RH", 2};
        List<Object[]> resultadoBruto = Arrays.asList(area1, area2);
        when(dashRepository.buscarTarefasPorArea(idEmpresa)).thenReturn(resultadoBruto);

        // Act
        List<Area> areas = empresaService.listarTarefasPorArea(idEmpresa);

        // Assert
        assertEquals(2, areas.size());
        assertEquals("TI", areas.get(0).getNome());
        assertEquals("RH", areas.get(1).getNome());
        verify(dashRepository).buscarTarefasPorArea(idEmpresa);
    }

    @Test
    void listarTarefasPorArea_deveRetornarListaVaziaQuandoNaoHaTarefas() {
        // Arrange
        Integer idEmpresa = 2;
        when(dashRepository.buscarTarefasPorArea(idEmpresa)).thenReturn(Collections.emptyList());

        // Act
        List<Area> areas = empresaService.listarTarefasPorArea(idEmpresa);

        // Assert
        assertTrue(areas.isEmpty());
        verify(dashRepository).buscarTarefasPorArea(idEmpresa);
    }

    @Test
    void listarFinanceiroPorEmpresa_deveRetornarInvestimentosCorretamente() {
        // Arrange
        Integer idEmpresa = 1;
        Investimento investimento1 = new Investimento();
        Investimento investimento2 = new Investimento();
        List<Investimento> investimentos = Arrays.asList(investimento1, investimento2);
        when(dashRepository.listarFinanceiroPorEmpresa(idEmpresa)).thenReturn(investimentos);

        InvestimentoResponseDto dto1 = new InvestimentoResponseDto();
        InvestimentoResponseDto dto2 = new InvestimentoResponseDto();

        try (MockedStatic<InvestimentoMapper> mapperMock = mockStatic(InvestimentoMapper.class)) {
            mapperMock.when(() -> InvestimentoMapper.toDto(investimento1)).thenReturn(dto1);
            mapperMock.when(() -> InvestimentoMapper.toDto(investimento2)).thenReturn(dto2);

            // Act
            List<InvestimentoResponseDto> dtos = empresaService.listarFinanceiroPorEmpresa(idEmpresa);

            // Assert
            assertEquals(2, dtos.size());
            assertTrue(dtos.contains(dto1));
            assertTrue(dtos.contains(dto2));
            verify(dashRepository).listarFinanceiroPorEmpresa(idEmpresa);
        }
    }

    @Test
    void listarFinanceiroPorEmpresa_deveRetornarListaVaziaQuandoNaoHaInvestimentos() {
        // Arrange
        Integer idEmpresa = 2;
        when(dashRepository.listarFinanceiroPorEmpresa(idEmpresa)).thenReturn(Collections.emptyList());

        // Act
        List<InvestimentoResponseDto> dtos = empresaService.listarFinanceiroPorEmpresa(idEmpresa);

        // Assert
        assertTrue(dtos.isEmpty());
        verify(dashRepository).listarFinanceiroPorEmpresa(idEmpresa);
    }
}
