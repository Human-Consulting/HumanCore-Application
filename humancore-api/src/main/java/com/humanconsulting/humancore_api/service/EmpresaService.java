package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.empresa.DashboardEmpresaResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.EmpresaMapper;
import com.humanconsulting.humancore_api.mapper.InvestimentoMapper;
import com.humanconsulting.humancore_api.model.Area;
import com.humanconsulting.humancore_api.model.Empresa;
import com.humanconsulting.humancore_api.model.Investimento;
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
        Empresa empresaCadastrada = empresaRepository.save(EmpresaMapper.toEntity(empresaRequestDto));
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

    public void deletar(Integer id) {
        empresaRepository.deleteById(id);
    }

    public EmpresaResponseDto atualizar(Integer idEmpresa, EmpresaAtualizarRequestDto request) {
        String urlImagemOriginal = buscarPorId(idEmpresa).getUrlImagem();
        if (request.getUrlImagem().isEmpty()) request.setUrlImagem(urlImagemOriginal);

        Empresa empresa = EmpresaMapper.toEntity(request);
        empresa.setIdEmpresa(idEmpresa);


        return passarParaResponse(empresa);
    }

    public EmpresaResponseDto passarParaResponse(Empresa empresa) {
        String nomeDiretor = String.valueOf(usuarioRepository.findDiretorByEmpresaId(empresa.getIdEmpresa()));
        Boolean comImpedimento = dashRepository.empresaComImpedimento(empresa.getIdEmpresa());
        Double progresso = dashRepository.mediaProgresso(empresa.getIdEmpresa());
        Double orcamento = dashRepository.orcamentoTotal(empresa.getIdEmpresa());
        return EmpresaMapper.toDto(empresa, nomeDiretor, comImpedimento, progresso, orcamento);
    }

    public DashboardEmpresaResponseDto criarDashboardResponse(Empresa empresa) {
        String nomeDiretor = String.valueOf(usuarioRepository.findDiretorByEmpresaId(empresa.getIdEmpresa()));
        Double progresso = dashRepository.mediaProgresso(empresa.getIdEmpresa());
        List<Area> areas = listarTarefasPorArea(empresa.getIdEmpresa());
        Double orcamento = dashRepository.orcamentoTotal(empresa.getIdEmpresa());
        Integer projetos = dashRepository.totalProjetos(empresa.getIdEmpresa());
        Boolean comImpedimento = dashRepository.empresaComImpedimento(empresa.getIdEmpresa());
        List<InvestimentoResponseDto> allResponse = listarFinanceiroPorEmpresa(empresa.getIdEmpresa());

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
