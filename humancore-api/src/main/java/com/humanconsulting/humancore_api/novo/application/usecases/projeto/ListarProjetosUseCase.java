package com.humanconsulting.humancore_api.novo.application.usecases.projeto;

import com.humanconsulting.humancore_api.novo.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.response.projeto.ProjetoResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ListarProjetosUseCase {
    private final ProjetoRepository projetoRepository;
    private final ProjetoResponseMapper projetoResponseMapper;

    public ListarProjetosUseCase(ProjetoRepository projetoRepository, ProjetoResponseMapper projetoResponseMapper) {
        this.projetoRepository = projetoRepository;
        this.projetoResponseMapper = projetoResponseMapper;
    }

    public List<ProjetoResponseDto> execute() {
        List<Projeto> all = projetoRepository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhum projeto registrado.");
        List<ProjetoResponseDto> allResponse = new ArrayList<>();
        for (Projeto projeto : all) {
            allResponse.add(projetoResponseMapper.toResponse(projeto, projeto.getResponsavel().getIdUsuario(), projeto.getIdProjeto()));
        }
        return allResponse;
    }
}

