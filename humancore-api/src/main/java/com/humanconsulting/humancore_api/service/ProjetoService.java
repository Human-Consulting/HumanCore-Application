package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.financeiro.FinanceiroResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.projeto.DashboardProjetoResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.FinanceiroMapper;
import com.humanconsulting.humancore_api.mapper.ProjetoMapper;
import com.humanconsulting.humancore_api.model.Area;
import com.humanconsulting.humancore_api.model.Financeiro;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    @Autowired private ProjetoRepository projetoRepository;

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired private TarefaRepository tarefaRepository;

    @Autowired private EmpresaRepository empresaRepository;

    @Autowired private DashboardProjetoRepository dashboardProjetoRepository;

    public ProjetoResponseDto cadastrar(ProjetoRequestDto projetoRequestDto) {
        if (projetoRepository.existsByNome(projetoRequestDto.getFkEmpresa(), projetoRequestDto.getDescricao())) throw new EntidadeConflitanteException("Projeto já cadastrado");

        Projeto projeto = projetoRepository.insert(ProjetoMapper.toEntity(projetoRequestDto));
        return passarParaResponse(projeto, projeto.getFkResponsavel(), projeto.getIdProjeto());
    }

    public Projeto buscarPorId(Integer id) {
        return projetoRepository.selectWhereId(id);
    }

    public List<ProjetoResponseDto> listar() {
        List<Projeto> all = projetoRepository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma projeto registrada");

        List<ProjetoResponseDto> allResponse = new ArrayList<>();
        for (Projeto projeto : all) {
            allResponse.add(passarParaResponse(projeto, projeto.getFkResponsavel(), projeto.getIdProjeto()));
        }
        return allResponse;
    }

    public void deletar(Integer id) {
        projetoRepository.deleteWhere(id);
    }

    public ProjetoResponseDto atualizar(Integer idProjeto, ProjetoAtualizarRequestDto projetoAtualizarRequestDto) {
        String urlImagemOriginal = buscarPorId(idProjeto).getUrlImagem();
        if (projetoAtualizarRequestDto.getUrlImagem().isEmpty()) projetoAtualizarRequestDto.setUrlImagem(urlImagemOriginal);
        Projeto projetoAtualizado = projetoRepository.update(idProjeto, projetoAtualizarRequestDto);
        return passarParaResponse(projetoAtualizado, projetoAtualizado.getFkResponsavel(), projetoAtualizado.getIdProjeto());
    }

    public List<ProjetoResponseDto> buscarPorIdEmpresa(Integer idEmpresa) {
     List<Projeto> all = projetoRepository.selectWhereIdEmpresa(idEmpresa);
     if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhum projeto encontrado");
     List<ProjetoResponseDto> allResponse = new ArrayList<>();
        for (Projeto projeto : all) {
            allResponse.add(passarParaResponse(projeto, projeto.getFkResponsavel(), projeto.getIdProjeto()));
        }
        return allResponse;
    }

    public ProjetoResponseDto passarParaResponse(Projeto projeto, Integer fkResponsavel, Integer idProjeto) {
        Usuario usuario = usuarioRepository.selectWhereId(fkResponsavel);
        double progresso = tarefaRepository.mediaProgressoProjeto(idProjeto);
        boolean comImpedimento = tarefaRepository.projetoComImpedimento(idProjeto);
        String urlImagemEmpresa = empresaRepository.getUrlImagemEmpresaByIdEmpresa(projeto.getFkEmpresa());
        return ProjetoMapper.toDto(projeto, usuario.getIdUsuario(), usuario.getNome(), progresso, comImpedimento, urlImagemEmpresa);
    }

    public DashboardProjetoResponseDto criarDashboardResponse(Projeto projeto) {
        String nomeDiretor = usuarioRepository.selectWhereId(projeto.getFkResponsavel()).getNome();
        Double progresso = dashboardProjetoRepository.mediaProgresso(projeto.getIdProjeto());
        List<Area> areas = listarTarefasPorArea(projeto.getIdProjeto());
        Double orcamento = dashboardProjetoRepository.orcamentoTotal(projeto.getIdProjeto());
        Integer projetos = dashboardProjetoRepository.totalSprints(projeto.getIdProjeto());
        Boolean comImpedimento = dashboardProjetoRepository.projetoComImpedimento(projeto.getIdProjeto());
        List<FinanceiroResponseDto> allResponse = listarFinanceiroPorProjeto(projeto.getIdProjeto());

        return ProjetoMapper.toDashboard(projeto, nomeDiretor, progresso, areas, orcamento, projetos, comImpedimento, allResponse);
    }

    public List<Area> listarTarefasPorArea(Integer idProjeto) {
        List<Object[]> resultadoBruto = dashboardProjetoRepository.buscarTarefasPorArea(idProjeto);

        return resultadoBruto.stream()
                .map(obj -> new Area(
                        (String) obj[0],
                        ((Number) obj[1]).intValue()
                ))
                .collect(Collectors.toList());
    }

    public List<FinanceiroResponseDto> listarFinanceiroPorProjeto(Integer idProjeto) {
        List<Financeiro> financeiros = dashboardProjetoRepository.listarFinanceiroPorEmpresa(idProjeto);
        List<FinanceiroResponseDto> financeiroResponseDtos = new ArrayList<>();
        for (Financeiro financeiro : financeiros) {
            financeiroResponseDtos.add(FinanceiroMapper.toDto(financeiro));
        }
        return financeiroResponseDtos;
    }
}
