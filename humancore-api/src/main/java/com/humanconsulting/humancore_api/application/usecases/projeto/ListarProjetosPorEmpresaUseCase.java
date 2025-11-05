package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.utils.PageResult;
import com.humanconsulting.humancore_api.infrastructure.utils.PageResultImpl;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ListarProjetosPorEmpresaUseCase {
    private final ProjetoRepository projetoRepository;
    private final ProjetoResponseMapper projetoResponseMapper;

    public ListarProjetosPorEmpresaUseCase(ProjetoRepository projetoRepository, ProjetoResponseMapper projetoResponseMapper) {
        this.projetoRepository = projetoRepository;
        this.projetoResponseMapper = projetoResponseMapper;
    }

    public PageResult<ProjetoResponseDto> execute(Integer idEmpresa, int page, int size, String nome) {
        PageResult<Projeto> pageResult = null;
        if (nome != null && !nome.isEmpty()) pageResult = projetoRepository.findAllByEmpresa_IdEmpresaAndNomeContainingIgnoreCase(idEmpresa, page, size, nome);
        else pageResult = projetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa, page, size);

        if (pageResult.getContent().isEmpty()) throw new EntidadeSemRetornoException("Nenhum projeto encontrado");
        List<ProjetoResponseDto> allResponse = new ArrayList<>();
        for (Projeto projeto : pageResult.getContent()) {
            allResponse.add(projetoResponseMapper.toResponse(projeto));
        }
        return new PageResultImpl<>(
                allResponse,
                pageResult.getPageNumber(),
                pageResult.getPageSize(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }
}

