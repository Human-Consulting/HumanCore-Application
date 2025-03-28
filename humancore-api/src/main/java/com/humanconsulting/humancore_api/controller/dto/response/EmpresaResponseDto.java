package com.humanconsulting.humancore_api.controller.dto.response;

import com.humanconsulting.humancore_api.model.Empresa;

public class EmpresaResponseDto {
    private Integer idEmpresa;
    private String nome;

    public EmpresaResponseDto() {
    }

    public EmpresaResponseDto(Integer idEmpresa, String nome) {
        this.idEmpresa = idEmpresa;
        this.nome = nome;
    }

    public static EmpresaResponseDto toResponse(Empresa empresa) {
        EmpresaResponseDto dto = new EmpresaResponseDto();

        dto.setIdEmpresa(empresa.getIdEmpresa());
        dto.setNome(empresa.getNome());
        return dto;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}
