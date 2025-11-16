package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.KpiProjetoResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.web.mappers.ProjetoMapper;

import java.util.ArrayList;
import java.util.List;

public class ListarProjetosKpisUseCase {
    private final ProjetoRepository projetoRepository;
    private final ProjetoResponseMapper projetoResponseMapper;
    private final ProjetoMapper projetoMapper;

    public ListarProjetosKpisUseCase(ProjetoRepository projetoRepository, ProjetoResponseMapper projetoResponseMapper, ProjetoMapper projetoMapper) {
        this.projetoRepository = projetoRepository;
        this.projetoResponseMapper = projetoResponseMapper;
        this.projetoMapper = projetoMapper;
    }

    public KpiProjetoResponseDto execute(Integer idEmpresa) {
        List<Projeto> projetosAll = projetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa);

        if (projetosAll.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma projeto registrada");

        List<ProjetoResponseDto> impedidos = new ArrayList<>();
        List<ProjetoResponseDto> finalizadas = new ArrayList<>();
        Integer totalAndamento = 0;

        for (Projeto projeto : projetosAll) {
            ProjetoResponseDto projetoResponseDto = projetoResponseMapper.toResponseKpi(projeto);
            if (projetoResponseDto.isComImpedimento()) impedidos.add(projetoResponseDto);
            else if (projetoResponseDto.getProgresso() == 100) finalizadas.add(projetoResponseDto);

            if(projetoResponseDto.getProgresso() < 100) totalAndamento++;
        }

        return ProjetoMapper.toKpiDto(impedidos, finalizadas, totalAndamento);
    }
}
