package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.DashboardEmpresaResponseDto;

public class CriarDashboardEmpresaUseCase {
    EmpresaResponseMapper empresaResponseMapper;
    BuscarEmpresaPorIdUseCase buscarEmpresaPorIdUseCase;

    public CriarDashboardEmpresaUseCase(EmpresaResponseMapper empresaResponseMapper, BuscarEmpresaPorIdUseCase buscarEmpresaPorIdUseCase) {
        this.empresaResponseMapper = empresaResponseMapper;
        this.buscarEmpresaPorIdUseCase = buscarEmpresaPorIdUseCase;
    }

    public DashboardEmpresaResponseDto execute(Integer idEmpresa) {
        Empresa empresa = buscarEmpresaPorIdUseCase.execute(idEmpresa);
        return empresaResponseMapper.toDashboardResponse(empresa);
    }
}

