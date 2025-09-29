package com.humanconsulting.humancore_api.novo.application.usecases.empresa;

import com.humanconsulting.humancore_api.novo.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.novo.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.PageResult;
import com.humanconsulting.humancore_api.novo.infrastructure.repositories.adapters.PageResultImpl;
import com.humanconsulting.humancore_api.novo.web.dtos.response.empresa.EmpresaResponseDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ListarEmpresasUseCase {
    private final EmpresaRepository empresaRepository;
    private final EmpresaResponseMapper empresaResponseMapper;

    public ListarEmpresasUseCase(EmpresaRepository empresaRepository, EmpresaResponseMapper empresaResponseMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaResponseMapper = empresaResponseMapper;
    }

    public PageResult<EmpresaResponseDto> execute(int page, int size) {
        PageResult<Empresa> empresas = empresaRepository.findAll(page, size);
        if (empresas.getContent().isEmpty()) throw new EntidadeSemRetornoException("Nenhuma empresa registrada");
        List<EmpresaResponseDto> allResponse = new ArrayList<>();
        for (Empresa empresa : empresas.getContent()) {
            String urlImagem = empresaRepository.findUrlImagemById(empresa.getIdEmpresa());
            empresa.setUrlImagem(urlImagem);
            allResponse.add(empresaResponseMapper.toResponse(empresa));
        }

        return new PageResultImpl<>(
                allResponse,
                empresas.getPageNumber(),
                empresas.getPageSize(),
                empresas.getTotalElements(),
                empresas.getTotalPages()
        );
    }
}

