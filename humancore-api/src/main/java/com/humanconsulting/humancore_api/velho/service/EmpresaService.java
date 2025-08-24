package com.humanconsulting.humancore_api.velho.service;

import com.humanconsulting.humancore_api.velho.controller.dto.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.velho.controller.dto.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.empresa.DashboardEmpresaResponseDto;
import com.humanconsulting.humancore_api.velho.controller.dto.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.velho.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.velho.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.velho.mapper.EmpresaMapper;
import com.humanconsulting.humancore_api.velho.mapper.InvestimentoMapper;
import com.humanconsulting.humancore_api.model.*;
import com.humanconsulting.humancore_api.velho.model.*;
import com.humanconsulting.humancore_api.velho.observer.SalaNotifier;
import com.humanconsulting.humancore_api.velho.repository.CheckpointRepository;
import com.humanconsulting.humancore_api.velho.repository.DashboardEmpresaRepository;
import com.humanconsulting.humancore_api.velho.repository.EmpresaRepository;
import com.humanconsulting.humancore_api.velho.repository.UsuarioRepository;
import com.humanconsulting.humancore_api.velho.security.PermissaoValidator;
import com.humanconsulting.humancore_api.velho.utils.ProgressoCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired EmpresaRepository empresaRepository;

    @Autowired CheckpointRepository checkpointRepository;

    @Autowired DashboardEmpresaRepository dashRepository;

    @Autowired UsuarioRepository usuarioRepository;

    @Autowired private SalaNotifier salaNotifier;

    public EmpresaResponseDto cadastrar(EmpresaRequestDto empresaRequestDto) {
        PermissaoValidator.validarPermissao(empresaRequestDto.getPermissaoEditor(), "ADICIONAR_EMPRESA");

        empresaRepository.existsByCnpj(empresaRequestDto.getCnpj());
        Empresa empresaCadastrada = empresaRepository.save(EmpresaMapper.toEntity(empresaRequestDto));

        salaNotifier.onEmpresaCriada(empresaCadastrada, usuarioRepository.findById(empresaRequestDto.getIdEditor()).get());
        return passarParaResponse(empresaCadastrada);
    }

    public Empresa buscarPorId(Integer id) {
        return empresaRepository.findById(id).get();
    }

    public List<EmpresaResponseDto> listar() {
        List<Empresa> all = empresaRepository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma empresa registrada");
        List<EmpresaResponseDto> allResponses = new ArrayList<>();
        for (Empresa empresa : all) {
            allResponses.add(passarParaResponse(empresa));
        }
        return allResponses;
    }

    public void deletar(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        PermissaoValidator.validarPermissao(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_EMPRESA");

        Optional<Empresa> optEmpresa = empresaRepository.findById(id);
        if (optEmpresa.isEmpty()) throw new EntidadeNaoEncontradaException("Empresa não encontrada.");
        empresaRepository.deleteById(id);
    }

    public EmpresaResponseDto atualizar(Integer idEmpresa, EmpresaAtualizarRequestDto request) {
        String urlImagemOriginal = empresaRepository.findUrlImagemById(idEmpresa);

        if (request.getUrlImagem().isEmpty()) request.setUrlImagem(urlImagemOriginal);

        Optional<Usuario> optUsuarioEditor = usuarioRepository.findById(request.getIdEditor());

        if (optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");

        PermissaoValidator.validarPermissao(request.getPermissaoEditor(), "MODIFICAR_EMPRESA");

        Empresa empresaAtualizada = empresaRepository.save(EmpresaMapper.toEntity(request, idEmpresa));
        return passarParaResponse(empresaAtualizada);
    }

    public EmpresaResponseDto passarParaResponse(Empresa empresa) {
        Optional<String> nomeDiretor = usuarioRepository.findDiretorByEmpresaId(empresa.getIdEmpresa());

        Boolean comImpedimento = dashRepository.empresaComImpedimento(empresa.getIdEmpresa());
        Double orcamento = dashRepository.orcamentoTotal(empresa.getIdEmpresa());

        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(empresa.getIdEmpresa());

        Double progresso = ProgressoCalculator.calularProgresso(checkpoints);

        return EmpresaMapper.toDto(empresa, nomeDiretor.orElse(null), comImpedimento, progresso, orcamento);
    }

    public DashboardEmpresaResponseDto criarDashboardResponse(Empresa empresa) {
        String nomeDiretor = usuarioRepository.findDiretorByEmpresaId(empresa.getIdEmpresa()).get();
        List<Area> areas = listarTarefasPorArea(empresa.getIdEmpresa());
        Double orcamento = dashRepository.orcamentoTotal(empresa.getIdEmpresa());
        Integer projetos = dashRepository.totalProjetos(empresa.getIdEmpresa());
        Boolean comImpedimento = dashRepository.empresaComImpedimento(empresa.getIdEmpresa());
        List<InvestimentoResponseDto> allResponse = listarFinanceiroPorEmpresa(empresa.getIdEmpresa());

        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_Sprint_Projeto_Empresa_IdEmpresa(empresa.getIdEmpresa());

        Double progresso = ProgressoCalculator.calularProgresso(checkpoints);

        return EmpresaMapper.toDashboard(empresa, nomeDiretor, progresso, areas, orcamento, projetos, comImpedimento, allResponse);
    }

    public List<Area> listarTarefasPorArea(Integer idEmpresa) {
        List<Object[]> resultadoBruto = dashRepository.buscarTarefasPorArea(idEmpresa);

        return resultadoBruto.stream()
                .map(obj -> new Area(
                        (String) obj[0],
                        ((Number) obj[1]).intValue()
                ))
                .collect(Collectors.toList());
    }

    public List<InvestimentoResponseDto> listarFinanceiroPorEmpresa(Integer idEmpresa) {
        List<Investimento> financeiros = dashRepository.listarFinanceiroPorEmpresa(idEmpresa);
        List<InvestimentoResponseDto> financeiroResponseDtos = new ArrayList<>();
        for (Investimento financeiro : financeiros) {
            financeiroResponseDtos.add(InvestimentoMapper.toDto(financeiro));
        }
        return financeiroResponseDtos;
    }
}
