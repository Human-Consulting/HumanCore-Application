package com.humanconsulting.humancore_api.application.usecases.projeto.mappers;

import com.humanconsulting.humancore_api.domain.entities.*;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.repositories.DashboardProjetoRepository;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.application.usecases.projeto.ListarFinanceiroPorProjetoUseCase;
import com.humanconsulting.humancore_api.application.usecases.projeto.ListarTarefasPorAreaUseCase;
import com.humanconsulting.humancore_api.application.usecases.projeto.ListarTarefasPorProjetoUsuarioUseCase;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.DashboardProjetoResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProjetoResponseMapperTest {

    private TarefaRepository tarefaRepository;
    private CheckpointRepository checkpointRepository;
    private UsuarioRepository usuarioRepository;
    private ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase;
    private ListarTarefasPorProjetoUsuarioUseCase listarTarefasPorProjetoUsuarioUseCase;
    private DashboardProjetoRepository dashboardProjetoRepository;
    private ListarFinanceiroPorProjetoUseCase listarFinanceiroPorProjetoUseCase;

    private ProjetoResponseMapper mapper;

    @BeforeEach
    void setup() {
        tarefaRepository = mock(TarefaRepository.class);
        checkpointRepository = mock(CheckpointRepository.class);
        usuarioRepository = mock(UsuarioRepository.class);
        listarTarefasPorAreaUseCase = mock(ListarTarefasPorAreaUseCase.class);
        listarTarefasPorProjetoUsuarioUseCase = mock(ListarTarefasPorProjetoUsuarioUseCase.class);
        dashboardProjetoRepository = mock(DashboardProjetoRepository.class);
        listarFinanceiroPorProjetoUseCase = mock(ListarFinanceiroPorProjetoUseCase.class);

        mapper = new ProjetoResponseMapper(
                tarefaRepository,
                checkpointRepository,
                usuarioRepository,
                listarTarefasPorAreaUseCase,
                dashboardProjetoRepository,
                listarFinanceiroPorProjetoUseCase,
                listarTarefasPorProjetoUsuarioUseCase
        );
    }

    @Test
    void testToResponse_Feliz() {
        Projeto projeto = new Projeto();

        projeto.setIdProjeto(1);
        projeto.setEmpresa(mock(Empresa.class));

        Checkpoint checkpoint = mock(Checkpoint.class);

        when(tarefaRepository.existsImpedimentoByProjeto(1)).thenReturn(false);
        when(checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(1))
                .thenReturn(List.of(checkpoint));

        ProjetoResponseDto response = mapper.toResponse(projeto);

        assertNotNull(response);
        assertEquals(1, response.getIdProjeto());
    }

    @Test
    void testToResponse_Triste_CheckpointsVazio() {
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(2);
        projeto.setEmpresa(mock(Empresa.class));

        when(tarefaRepository.existsImpedimentoByProjeto(2)).thenReturn(true);
        when(checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(2))
                .thenReturn(Collections.emptyList());

        ProjetoResponseDto response = mapper.toResponse(projeto);

        assertNotNull(response);
        assertEquals(0.0, response.getProgresso());
    }

    @Test
    void testToResponseDashboard_Feliz() {
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(3);

        Area e1 = mock(Area.class);

        TarefaUsuario e2 = mock(TarefaUsuario.class);

        Checkpoint e3 = mock(Checkpoint.class);

        when(listarTarefasPorAreaUseCase.execute(3)).thenReturn(List.of(e1));
        when(listarTarefasPorProjetoUsuarioUseCase.execute(3)).thenReturn(List.of(e2));
        when(dashboardProjetoRepository.orcamentoTotal(3)).thenReturn(1000.0);
        when(dashboardProjetoRepository.totalSprints(3)).thenReturn(5);
        when(dashboardProjetoRepository.projetoComImpedimento(3)).thenReturn(false);
        when(listarFinanceiroPorProjetoUseCase.execute(3)).thenReturn(Collections.emptyList());
        when(checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(3)).thenReturn(List.of(e3));

        DashboardProjetoResponseDto response = mapper.toResponseDashboard(projeto);

        assertNotNull(response);
        assertEquals(1000.0, response.getOrcamento());
    }

    @Test
    void testToResponseDashboard_Triste_SemDados() {
        Projeto projeto = new Projeto();
        projeto.setIdProjeto(4);

        when(listarTarefasPorAreaUseCase.execute(4)).thenReturn(Collections.emptyList());
        when(listarTarefasPorProjetoUsuarioUseCase.execute(4)).thenReturn(Collections.emptyList());
        when(dashboardProjetoRepository.orcamentoTotal(4)).thenReturn(null);
        when(dashboardProjetoRepository.totalSprints(4)).thenReturn(0);
        when(dashboardProjetoRepository.projetoComImpedimento(4)).thenReturn(true);
        when(listarFinanceiroPorProjetoUseCase.execute(4)).thenReturn(Collections.emptyList());
        when(checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(4)).thenReturn(Collections.emptyList());

        DashboardProjetoResponseDto response = mapper.toResponseDashboard(projeto);

        assertNotNull(response);
        assertEquals(0.0, response.getProgresso());
    }
}
