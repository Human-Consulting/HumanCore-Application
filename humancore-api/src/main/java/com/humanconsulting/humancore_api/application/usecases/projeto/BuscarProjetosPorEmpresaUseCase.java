package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;

import java.util.ArrayList;
import java.util.List;

public class BuscarProjetosPorEmpresaUseCase {
    private final ProjetoRepository projetoRepository;
    private final ProjetoResponseMapper projetoResponseMapper;

    public BuscarProjetosPorEmpresaUseCase(ProjetoRepository projetoRepository, ProjetoResponseMapper projetoResponseMapper) {
        this.projetoRepository = projetoRepository;
        this.projetoResponseMapper = projetoResponseMapper;
    }

    public List<ProjetoResponseDto> execute(Integer idEmpresa) {
        List<Projeto> all = projetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhum projeto encontrado");
        List<ProjetoResponseDto> allResponse = new ArrayList<>();
        for (Projeto projeto : all) {
            allResponse.add(projetoResponseMapper.toResponse(projeto, projeto.getResponsavel().getIdUsuario(), projeto.getIdProjeto()));
        }
        return allResponse;
    }
}

