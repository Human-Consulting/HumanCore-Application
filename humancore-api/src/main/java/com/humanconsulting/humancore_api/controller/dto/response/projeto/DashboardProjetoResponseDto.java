package com.humanconsulting.humancore_api.controller.dto.response.projeto;

import com.humanconsulting.humancore_api.controller.dto.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.model.Area;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

public class DashboardProjetoResponseDto {
    @Schema(description = "ID do projeto", example = "1")
    private Integer idProjeto;

    @Schema(description = "Nome do responsável pelo projeto", example = "João Silva")
    private String nomeResponsavel;

    @Schema(description = "Indica se há impedimento no projeto", example = "false")
    private boolean comImpedimento;

    @Schema(description = "Progresso atual do projeto, em porcentagem", example = "75.5")
    private Double progresso;

    @Schema(description = "Valor do orçamento do projeto", example = "1000000.00")
    private Double orcamento;

    @Schema(description = "Lista de áreas envolvidas no projeto", implementation = Area.class)
    private List<Area> areas;

    @Schema(description = "Lista de informações financeiras do projeto", implementation = InvestimentoResponseDto.class)
    private List<InvestimentoResponseDto> financeiroResponseDtos;

    @Schema(description = "Total de itens no projeto", example = "10")
    private Integer totalItens;

    public DashboardProjetoResponseDto(Integer idProjeto, String nomeResponsavel, boolean comImpedimento, Double progreso, Double orcamento, List<Area> areas, Integer totalItens, List<InvestimentoResponseDto> financeiroResponseDtos) {
        this.idProjeto = idProjeto;
        this.nomeResponsavel = nomeResponsavel;
        this.comImpedimento = comImpedimento;
        this.progresso = progreso;
        this.orcamento = orcamento;
        this.areas = areas;
        this.totalItens = totalItens;
        this.financeiroResponseDtos = financeiroResponseDtos;
    }

    public Integer getIdProjeto() {
        return idProjeto;
    }

    public void setIdProjeto(Integer idProjeto) {
        this.idProjeto = idProjeto;
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
