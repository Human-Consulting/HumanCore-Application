package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.EmpresaResponseDto;
import com.humanconsulting.humancore_api.model.Empresa;

public class EmpresaMapper {
    public static Empresa toEntity(EmpresaRequestDto empresaRequestDto) {
        return new Empresa(null, empresaRequestDto.getCnpj(), empresaRequestDto.getNome());
    }

    public static EmpresaResponseDto toDto(Empresa empresa) {
        return new EmpresaResponseDto(empresa.getIdEmpresa(), empresa.getNome());
    }
}
