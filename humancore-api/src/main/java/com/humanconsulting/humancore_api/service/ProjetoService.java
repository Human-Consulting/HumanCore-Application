package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.projeto.DashboardProjetoResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.InvestimentoMapper;
import com.humanconsulting.humancore_api.mapper.ProjetoMapper;
import com.humanconsulting.humancore_api.model.*;
import com.humanconsulting.humancore_api.repository.*;
import com.humanconsulting.humancore_api.security.PermissaoValidator;
import com.humanconsulting.humancore_api.utils.ProgressoCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjetoService {

    @Autowired private ProjetoRepository projetoRepository;

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired private TarefaRepository tarefaRepository;

    @Autowired private CheckpointRepository checkpointRepository;

    @Autowired private SprintRepository sprintRepository;

    @Autowired private EmpresaRepository empresaRepository;

    @Autowired private DashboardProjetoRepository dashboardProjetoRepository;

    public ProjetoResponseDto cadastrar(ProjetoRequestDto projetoRequestDto) {
        PermissaoValidator.validarPermissao(projetoRequestDto.getPermissaoEditor(), "ADICIONAR_PROJETO");

        if (projetoRepository.existsByEmpresa_IdEmpresaAndDescricao(projetoRequestDto.getFkEmpresa(), projetoRequestDto.getDescricao())) throw new EntidadeConflitanteException("Projeto já cadastrado");

        Projeto projeto = projetoRepository.save(ProjetoMapper.toEntity(projetoRequestDto, empresaRepository.findById(projetoRequestDto.getFkEmpresa()).get(), usuarioRepository.findById(projetoRequestDto.getFkResponsavel()).get()));
        return passarParaResponse(projeto, projeto.getResponsavel().getIdUsuario(), projeto.getIdProjeto());
    }

    public Projeto buscarPorId(Integer id) {
       Optional<Projeto> optProjeto = projetoRepository.findById(id);
        if (optProjeto.isEmpty()) throw new EntidadeSemRetornoException("Nenhum projeto encontrado.");

        return optProjeto.get();
    }

    public List<ProjetoResponseDto> listar() {
        List<Projeto> all = projetoRepository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhum projeto registrado.");

        List<ProjetoResponseDto> allResponse = new ArrayList<>();
        for (Projeto projeto : all) {
            allResponse.add(passarParaResponse(projeto, projeto.getResponsavel().getIdUsuario(), projeto.getIdProjeto()));
        }
        return allResponse;
    }

    public void deletar(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        PermissaoValidator.validarPermissao(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_PROJETO");
        buscarPorId(id);

        projetoRepository.deleteById(id);
    }

    public ProjetoResponseDto atualizar(Integer idProjeto, ProjetoAtualizarRequestDto projetoAtualizarRequestDto) {
        Projeto projeto = buscarPorId(idProjeto);

        String urlImagemOriginal = projetoRepository.findUrlImagemById(idProjeto);

        if (projetoAtualizarRequestDto.getUrlImagem().isEmpty()) projetoAtualizarRequestDto.setUrlImagem(urlImagemOriginal);

        Optional<Usuario> optUsuarioEditor = usuarioRepository.findById(projetoAtualizarRequestDto.getIdEditor());

        if (optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");

        PermissaoValidator.validarPermissao(projetoAtualizarRequestDto.getPermissaoEditor(), "MODIFICAR_PROJETO");

        Usuario usuario = usuarioRepository.findById(projetoAtualizarRequestDto.getFkResponsavel()).get();

        Projeto projetoAtualizado = projetoRepository.save(ProjetoMapper.toEntity(projetoAtualizarRequestDto, idProjeto, usuario, projeto.getEmpresa()));

        return passarParaResponse(projetoAtualizado, projetoAtualizado.getResponsavel().getIdUsuario(), projetoAtualizado.getIdProjeto());
    }

    public List<ProjetoResponseDto> buscarPorIdEmpresa(Integer idEmpresa) {
     List<Projeto> all = projetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa);
     if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhum projeto encontrado");
     List<ProjetoResponseDto> allResponse = new ArrayList<>();
        for (Projeto projeto : all) {
            allResponse.add(passarParaResponse(projeto, projeto.getResponsavel().getIdUsuario(), projeto.getIdProjeto()));
        }
        return allResponse;
    }

    public ProjetoResponseDto passarParaResponse(Projeto projeto, Integer fkResponsavel, Integer idProjeto) {

        List<Sprint> sprints = sprintRepository.findAll();

        boolean comImpedimento = tarefaRepository.existsImpedimentoByProjeto(idProjeto);

        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(idProjeto);

        Double progresso = ProgressoCalculator.calularProgresso(checkpoints);

        return ProjetoMapper.toDto(projeto, progresso, comImpedimento);
    }

    public DashboardProjetoResponseDto criarDashboardResponse(Projeto projeto) {
        Optional<Usuario> usuario = usuarioRepository.findById(projeto.getResponsavel().getIdUsuario());
        String nomeDiretor = usuario.get().getNome();
        List<Area> areas = listarTarefasPorArea(projeto.getIdProjeto());
        Double orcamento = dashboardProjetoRepository.orcamentoTotal(projeto.getIdProjeto());
        Integer projetos = dashboardProjetoRepository.totalSprints(projeto.getIdProjeto());
        Boolean comImpedimento = dashboardProjetoRepository.projetoComImpedimento(projeto.getIdProjeto());
        List<InvestimentoResponseDto> allResponse = listarFinanceiroPorProjeto(projeto.getIdProjeto());

        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_IdProjeto(projeto.getIdProjeto());

        Double progresso = ProgressoCalculator.calularProgresso(checkpoints);

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

    public List<InvestimentoResponseDto> listarFinanceiroPorProjeto(Integer idProjeto) {
        List<Investimento> financeiros = dashboardProjetoRepository.listarFinanceiroPorEmpresa(idProjeto);
        List<InvestimentoResponseDto> financeiroResponseDtos = new ArrayList<>();
        for (Investimento financeiro : financeiros) {
            financeiroResponseDtos.add(InvestimentoMapper.toDto(financeiro));
        }
        return financeiroResponseDtos;
    }
}
