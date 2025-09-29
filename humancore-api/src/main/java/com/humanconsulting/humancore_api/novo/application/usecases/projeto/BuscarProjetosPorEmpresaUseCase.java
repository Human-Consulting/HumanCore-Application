package com.humanconsulting.humancore_api.novo.application.usecases.projeto;

import com.humanconsulting.humancore_api.novo.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.novo.domain.repositories.PageResult;
import com.humanconsulting.humancore_api.novo.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.response.projeto.ProjetoResponseDto;

import java.util.ArrayList;
import java.util.List;

public class BuscarProjetosPorEmpresaUseCase {
    private final ProjetoRepository projetoRepository;
    private final ProjetoResponseMapper projetoResponseMapper;

    public BuscarProjetosPorEmpresaUseCase(ProjetoRepository projetoRepository, ProjetoResponseMapper projetoResponseMapper) {
        this.projetoRepository = projetoRepository;
        this.projetoResponseMapper = projetoResponseMapper;
    }

    public PageResult<ProjetoResponseDto> execute(Integer idEmpresa, int page, int size) {
        PageResult<Projeto> pageResult = projetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa, page, size);
        if (pageResult.getContent().isEmpty()) throw new EntidadeSemRetornoException("Nenhum projeto encontrado");
        List<ProjetoResponseDto> allResponse = new ArrayList<>();
        for (Projeto projeto : pageResult.getContent()) {
            allResponse.add(projetoResponseMapper.toResponse(projeto, projeto.getResponsavel().getIdUsuario(), projeto.getIdProjeto()));
        }
        return new com.humanconsulting.humancore_api.novo.infrastructure.repositories.adapters.PageResultImpl<>(
            allResponse,
            pageResult.getPageNumber(),
            pageResult.getPageSize(),
            pageResult.getTotalElements(),
            pageResult.getTotalPages()
        );
    }
}
