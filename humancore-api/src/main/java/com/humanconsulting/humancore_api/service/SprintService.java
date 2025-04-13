package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.sprint.*;
import com.humanconsulting.humancore_api.controller.dto.request.SprintRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.EntregaResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.SprintResponseDto;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.SprintMapper;
import com.humanconsulting.humancore_api.model.Entrega;
import com.humanconsulting.humancore_api.model.Sprint;
import com.humanconsulting.humancore_api.repository.EntregaRepository;
import com.humanconsulting.humancore_api.repository.SprintRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SprintService {

    @Autowired private SprintRepository sprintRepository;

    @Autowired private EntregaRepository entregaRepository;

    @Autowired private EntregaService entregaService;

    public SprintResponseDto cadastrar(SprintRequestDto sprintRequestDto) {
        if (sprintRequestDto.getDtInicio().isAfter(sprintRequestDto.getDtFim()) || sprintRequestDto.getDtInicio().isEqual(sprintRequestDto.getDtFim())) throw new EntidadeConflitanteException("Datas de início e fim conflitantes.");
        Sprint sprint = sprintRepository.insert(SprintMapper.toEntity(sprintRequestDto));
        return passarParaResponse(sprint, sprint.getIdSprint());
    }

    public Sprint buscarPorId(Integer id) {
        return sprintRepository.selectWhereId(id);
    }

    public List<Sprint> listar() {
        List<Sprint> all = sprintRepository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma sprint registrada");
        return all;
    }

    public void deletar(Integer id) {
        sprintRepository.deleteWhere(id);
    }

    public SprintResponseDto atualizar(Integer idSprint, SprintAtualizarRequestDto request) {
        Boolean temPermissao = sprintRepository.validarPermissao(request.getIdEditor(), request.getPermissaoEditor());
        if (!temPermissao) throw new EntidadeSemPermissaoException("Você não tem permissão para fazer essa edição");

        sprintRepository.existsById(idSprint);

        Sprint sprint = sprintRepository.update(idSprint, request);
        return passarParaResponse(sprint, sprint.getIdSprint());
    }

    public List<SprintResponseDto> buscarPorIdProjeto(Integer idProjeto) {
        List<Sprint> all = sprintRepository.selectWhereIdProjeto(idProjeto);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhum projeto encontrado");
        List<SprintResponseDto> allResponse = new ArrayList<>();
        for (Sprint sprint : all) {
            allResponse.add(passarParaResponse(sprint, sprint.getIdSprint()));
        }
        return allResponse;
    }

    public SprintResponseDto passarParaResponse(Sprint sprint, Integer idSprint) {
        double progresso = entregaRepository.mediaProgressoSprint(idSprint);
        boolean comImpedimento = entregaRepository.projetoComImpedimento(idSprint);
        List<Entrega> entregas = entregaRepository.selectWhereIdProjeto(sprint.getFkProjeto(), sprint.getIdSprint());
        List<EntregaResponseDto> entregasResponse = new ArrayList<>();
        for (Entrega entrega : entregas) {
            entregasResponse.add(entregaService.passarParaResponse(entrega, entrega.getFkResponsavel()));
        }
        return SprintMapper.toDto(sprint, progresso, comImpedimento, entregasResponse);
    }
}
