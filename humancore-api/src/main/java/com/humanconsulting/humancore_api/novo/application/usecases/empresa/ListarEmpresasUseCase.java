package com.humanconsulting.humancore_api.novo.application.usecases.empresa;

import com.humanconsulting.humancore_api.novo.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.novo.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.response.empresa.EmpresaResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class ListarEmpresasUseCase {
    private final EmpresaRepository empresaRepository;
    private final EmpresaResponseMapper empresaResponseMapper;

    public ListarEmpresasUseCase(EmpresaRepository empresaRepository, EmpresaResponseMapper empresaResponseMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaResponseMapper = empresaResponseMapper;
    }

    public List<EmpresaResponseDto> execute() {
        List<Empresa> empresas = empresaRepository.findAll();
        if (empresas.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma empresa registrada");
        return empresas.stream()
                .map(empresaResponseMapper::toResponse)
                .collect(Collectors.toList());
    }
}

