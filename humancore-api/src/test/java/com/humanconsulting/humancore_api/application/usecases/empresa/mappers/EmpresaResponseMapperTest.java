package com.humanconsulting.humancore_api.application.usecases.empresa.mappers;

import com.humanconsulting.humancore_api.application.usecases.empresa.ListarFinanceiroPorEmpresaUseCase;
import com.humanconsulting.humancore_api.application.usecases.empresa.ListarTarefasPorAreaUseCase;
import com.humanconsulting.humancore_api.application.usecases.empresa.ListarTarefasPorEmpresaUsuarioUseCase;
import com.humanconsulting.humancore_api.domain.entities.Area;
import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.TarefaUsuario;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.repositories.DashboardEmpresaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.DashboardEmpresaResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class EmpresaResponseMapperTest {

    private UsuarioRepository usuarioRepository;
    private DashboardEmpresaRepository dashRepository;
    private CheckpointRepository checkpointRepository;
    private ListarTarefasPorAreaUseCase listarTarefasPorAreaUseCase;
    private ListarTarefasPorEmpresaUsuarioUseCase listarTarefasPorEmpresaUsuarioUseCase;
    private ListarFinanceiroPorEmpresaUseCase listarFinanceiroPorEmpresaUseCase;
    private EmpresaResponseMapper mapper;

    @BeforeEach
    void setup() {
        usuarioRepository = mock(UsuarioRepository.class);
        dashRepository = mock(DashboardEmpresaRepository.class);
        checkpointRepository = mock(CheckpointRepository.class);
        listarTarefasPorAreaUseCase = mock(ListarTarefasPorAreaUseCase.class);
        listarTarefasPorEmpresaUsuarioUseCase = mock(ListarTarefasPorEmpresaUsuarioUseCase.class);
        listarFinanceiroPorEmpresaUseCase = mock(ListarFinanceiroPorEmpresaUseCase.class);

        mapper = new EmpresaResponseMapper(
                usuarioRepository,
                dashRepository,
                checkpointRepository,
                listarTarefasPorAreaUseCase,
                listarFinanceiroPorEmpresaUseCase,
                listarTarefasPorEmpresaUsuarioUseCase
        );
    }

    @Test
    void testToResponse_Feliz() {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(1);
        empresa.setNome("Empresa Teste");

        Usuario diretor = new Usuario();
        diretor.setIdUsuario(10);
        diretor.setNome("Diretor");

        when(usuarioRepository.findDiretorByEmpresaId(1)).thenReturn(diretor);
        when(dashRepository.empresaComImpedimento(1)).thenReturn(false);
        when(dashRepository.orcamentoTotal(1)).thenReturn(5000.0);
        when(checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(1))
                .thenReturn(List.of(mock(Checkpoint.class)));

        EmpresaResponseDto response = mapper.toResponse(empresa);

        assertNotNull(response);
        assertEquals(1, response.getIdEmpresa());
        assertEquals("Empresa Teste", response.getNome());
        assertEquals(5000.0, response.getOrcamento());
    }

    @Test
    void testToResponse_Triste_SemCheckpoints() {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(2);

        Usuario diretor = new Usuario();
        diretor.setIdUsuario(20);

        when(usuarioRepository.findDiretorByEmpresaId(2)).thenReturn(diretor);
        when(dashRepository.empresaComImpedimento(2)).thenReturn(true);
        when(dashRepository.orcamentoTotal(2)).thenReturn(null);
        when(checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(2))
                .thenReturn(Collections.emptyList());

        EmpresaResponseDto response = mapper.toResponse(empresa);

        assertNotNull(response);
        assertEquals(2, response.getIdEmpresa());
        assertEquals(0.0, response.getProgresso()); // progresso calculado com lista vazia
    }

    @Test
    void testToDashboardResponse_Feliz() {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(3);

        Usuario diretor = new Usuario();
        diretor.setIdUsuario(30);

        when(usuarioRepository.findDiretorByEmpresaId(3)).thenReturn(diretor);
        when(listarTarefasPorAreaUseCase.execute(3)).thenReturn(List.of(mock(Area.class)));
        when(listarTarefasPorEmpresaUsuarioUseCase.execute(3)).thenReturn(List.of(mock(TarefaUsuario.class)));
        when(dashRepository.orcamentoTotal(3)).thenReturn(10000.0);
        when(dashRepository.totalProjetos(3)).thenReturn(4);
        when(dashRepository.empresaComImpedimento(3)).thenReturn(false);
        when(listarFinanceiroPorEmpresaUseCase.execute(3)).thenReturn(Collections.emptyList());
        when(checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(3))
                .thenReturn(List.of(mock(Checkpoint.class)));

        DashboardEmpresaResponseDto response = mapper.toDashboardResponse(empresa);

        assertNotNull(response);
        assertEquals(10000.0, response.getOrcamento());
    }

    @Test
    void testToDashboardResponse_Triste_SemDados() {
        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(4);

        Usuario diretor = new Usuario();
        diretor.setIdUsuario(40);

        when(usuarioRepository.findDiretorByEmpresaId(4)).thenReturn(diretor);
        when(listarTarefasPorAreaUseCase.execute(4)).thenReturn(Collections.emptyList());
        when(listarTarefasPorEmpresaUsuarioUseCase.execute(4)).thenReturn(Collections.emptyList());
        when(dashRepository.orcamentoTotal(4)).thenReturn(null);
        when(dashRepository.totalProjetos(4)).thenReturn(0);
        when(dashRepository.empresaComImpedimento(4)).thenReturn(true);
        when(listarFinanceiroPorEmpresaUseCase.execute(4)).thenReturn(Collections.emptyList());
        when(checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(4))
                .thenReturn(Collections.emptyList());

        DashboardEmpresaResponseDto response = mapper.toDashboardResponse(empresa);

        assertNotNull(response);
        assertEquals(0.0, response.getProgresso());
    }
}

