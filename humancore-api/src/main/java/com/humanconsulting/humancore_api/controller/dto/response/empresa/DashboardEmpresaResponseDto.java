package com.humanconsulting.humancore_api.controller.dto.response.empresa;

import com.humanconsulting.humancore_api.controller.dto.response.financeiro.FinanceiroResponseDto;
import com.humanconsulting.humancore_api.model.Area;

import java.util.List;

public class DashboardEmpresaResponseDto {
    private Integer idEmpresa;

    private String nomeResponsavel;

    private boolean comImpedimento;

    private Double progresso;

    private Double orcamento;

    private List<Area> areas;

    private List<FinanceiroResponseDto> financeiroResponseDtos;

    private Integer totalItens;

    public DashboardEmpresaResponseDto(Integer idEmpresa, String nomeResponsavel, boolean comImpedimento, Double progreso, Double orcamento, List<Area> areas, Integer totalItens, List<FinanceiroResponseDto> financeiroResponseDtos) {
        this.idEmpresa = idEmpresa;
        this.nomeResponsavel = nomeResponsavel;
        this.comImpedimento = comImpedimento;
        this.progresso = progreso;
        this.orcamento = orcamento;
        this.areas = areas;
        this.totalItens = totalItens;
        this.financeiroResponseDtos = financeiroResponseDtos;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public void setNomeResponsavel(String nomeResponsavel) {
        this.nomeResponsavel = nomeResponsavel;
    }

    public boolean isComImpedimento() {
        return comImpedimento;
    }

    public void setComImpedimento(boolean comImpedimento) {
        this.comImpedimento = comImpedimento;
    }

    public Double getProgresso() {
        return progresso;
    }

    public void setProgresso(Double progresso) {
        this.progresso = progresso;
    }

    public Double getOrcamento() {
        return orcamento;
    }

    public void setOrcamento(Double orcamento) {
        this.orcamento = orcamento;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public Integer getTotalItens() {
        return totalItens;
    }

    public void setTotalItens(Integer totalItens) {
        this.totalItens = totalItens;
    }

    public List<FinanceiroResponseDto> getFinanceiroResponseDtos() {
        return financeiroResponseDtos;
    }

    public void setFinanceiroResponseDtos(List<FinanceiroResponseDto> financeiroResponseDtos) {
        this.financeiroResponseDtos = financeiroResponseDtos;
    }
}
