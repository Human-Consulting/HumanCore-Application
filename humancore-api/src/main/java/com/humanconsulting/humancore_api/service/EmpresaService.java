package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.financeiro.FinanceiroResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.empresa.DashboardEmpresaResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.EmpresaMapper;
import com.humanconsulting.humancore_api.mapper.FinanceiroMapper;
import com.humanconsulting.humancore_api.model.Area;
import com.humanconsulting.humancore_api.model.Empresa;
import com.humanconsulting.humancore_api.model.Financeiro;
import com.humanconsulting.humancore_api.repository.DashboardEmpresaRepository;
import com.humanconsulting.humancore_api.repository.EmpresaRepository;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmpresaService {

    @Autowired private EmpresaRepository empresaRepository;

    @Autowired private DashboardEmpresaRepository dashRepository;

    @Autowired private UsuarioRepository usuarioRepository;

    public EmpresaResponseDto cadastrar(EmpresaRequestDto empresaRequestDto) {
        empresaRepository.existsByCnpj(empresaRequestDto.getCnpj());
        Empresa empresaCadastrada = empresaRepository.insert(EmpresaMapper.toEntity(empresaRequestDto));
        return passarParaResponse(empresaCadastrada);
    }

    public Empresa buscarPorId(Integer id) {
        return empresaRepository.selectWhereId(id);
    }

    public List<EmpresaResponseDto> listar() {
        List<Empresa> all = empresaRepository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma empresa registrada");
        List<EmpresaResponseDto> allResponses = new ArrayList<>();
        for (Empresa empresa : all) {
            allResponses.add(passarParaResponse(empresa));
        }
        return allResponses;
    }

    public void deletar(Integer id) {
        empresaRepository.deleteWhere(id);
    }

    public EmpresaResponseDto atualizar(Integer idEmpresa, EmpresaAtualizarRequestDto request) {
        String urlImagemOriginal = buscarPorId(idEmpresa).getUrlImagem();
        if (request.getUrlImagem().isEmpty()) request.setUrlImagem(urlImagemOriginal);
        return passarParaResponse(empresaRepository.update(idEmpresa, request));
    }

    public EmpresaResponseDto passarParaResponse(Empresa empresa) {
        String nomeDiretor = usuarioRepository.getDiretor(empresa.getIdEmpresa());
        Boolean comImpedimento = dashRepository.empresaComImpedimento(empresa.getIdEmpresa());
        Double progresso = dashRepository.mediaProgresso(empresa.getIdEmpresa());
        Double orcamento = dashRepository.orcamentoTotal(empresa.getIdEmpresa());
        return EmpresaMapper.toDto(empresa, nomeDiretor, comImpedimento, progresso, orcamento);
    }

    public DashboardEmpresaResponseDto criarDashboardResponse(Empresa empresa) {
        String nomeDiretor = usuarioRepository.getDiretor(empresa.getIdEmpresa());
        Double progresso = dashRepository.mediaProgresso(empresa.getIdEmpresa());
        List<Area> areas = listarTarefasPorArea(empresa.getIdEmpresa());
        Double orcamento = dashRepository.orcamentoTotal(empresa.getIdEmpresa());
        Integer projetos = dashRepository.totalProjetos(empresa.getIdEmpresa());
        Boolean comImpedimento = dashRepository.empresaComImpedimento(empresa.getIdEmpresa());
        List<FinanceiroResponseDto> allResponse = listarFinanceiroPorEmpresa(empresa.getIdEmpresa());

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

    public List<FinanceiroResponseDto> listarFinanceiroPorEmpresa(Integer idEmpresa) {
        List<Financeiro> financeiros = dashRepository.listarFinanceiroPorEmpresa(idEmpresa);
        List<FinanceiroResponseDto> financeiroResponseDtos = new ArrayList<>();
        for (Financeiro financeiro : financeiros) {
            financeiroResponseDtos.add(FinanceiroMapper.toDto(financeiro));
        }
        return financeiroResponseDtos;
    }
}
