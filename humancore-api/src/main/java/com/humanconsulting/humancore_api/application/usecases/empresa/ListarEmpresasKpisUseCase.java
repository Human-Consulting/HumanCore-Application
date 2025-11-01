package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.KpiEmpresaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.EmpresaMapper;

import java.util.ArrayList;
import java.util.List;

public class ListarEmpresasKpisUseCase {
    private final EmpresaRepository empresaRepository;
    private final EmpresaResponseMapper empresaResponseMapper;
    private final EmpresaMapper empresaMapper;

    public ListarEmpresasKpisUseCase(EmpresaRepository empresaRepository, EmpresaResponseMapper empresaResponseMapper, EmpresaMapper empresaMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaResponseMapper = empresaResponseMapper;
        this.empresaMapper = empresaMapper;
    }

    public KpiEmpresaResponseDto execute() {
        List<Empresa> empresasAll = empresaRepository.findAll();

        if (empresasAll.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma empresa registrada");

        List<EmpresaResponseDto> impedidos = new ArrayList<>();
        List<EmpresaResponseDto> finalizadas = new ArrayList<>();
        Integer totalAndamento = 0;

        for (Empresa empresa : empresasAll) {
            EmpresaResponseDto empresaResponseDto = empresaResponseMapper.toResponseKpi(empresa);
            if (empresaResponseDto.isComImpedimento()) impedidos.add(empresaResponseDto);
            else if (empresaResponseDto.getProgresso() == 100) finalizadas.add(empresaResponseDto);

            if(empresaResponseDto.getProgresso() < 100) totalAndamento++;
        }

        return EmpresaMapper.toKpiDto(impedidos, finalizadas, totalAndamento);
    }
}

