package com.humanconsulting.humancore_api.controller.dto.response.empresa;

import com.humanconsulting.humancore_api.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.model.Area;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardEmpresaResponseDto {
    @Schema(description = "ID da Empresa", example = "1")
    private Integer idEmpresa;

    @Schema(description = "Nome completo do Responsável pela Empresa", example = "Carlos Silva")
    private String nomeResponsavel;

    @Schema(description = "Indica se a empresa possui algum projeto com impedimentos", example = "false")
    private boolean comImpedimento;

    @Schema(description = "Progresso total da empresa em porcentagem", example = "75.5")
    private Double progresso;

    @Schema(description = "Orçamento total da empresa", example = "1000000.0")
    private Double orcamento;

    @Schema(description = "Áreas associadas à empresa", example = "[\"TI\", \"Investimento\"]")
    private List<Area> areas;

    @Schema(description = "Lista de dados financeiros da empresa", implementation = InvestimentoResponseDto.class)
    private List<InvestimentoResponseDto> financeiroResponseDtos;

    @Schema(description = "Total de itens da empresa", example = "25")
    private Integer totalItens;

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

    public List<InvestimentoResponseDto> getFinanceiroResponseDtos() {
        return financeiroResponseDtos;
    }

    public void setFinanceiroResponseDtos(List<InvestimentoResponseDto> financeiroResponseDtos) {
        this.financeiroResponseDtos = financeiroResponseDtos;
    }
}
