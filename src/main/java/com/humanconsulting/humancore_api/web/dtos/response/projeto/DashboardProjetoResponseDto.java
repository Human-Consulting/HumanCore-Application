package com.humanconsulting.humancore_api.web.dtos.response.projeto;

import com.humanconsulting.humancore_api.domain.entities.TarefaUsuario;
import com.humanconsulting.humancore_api.web.dtos.response.investimento.InvestimentoResponseDto;
import com.humanconsulting.humancore_api.domain.entities.Area;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioSprintResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardProjetoResponseDto {
    @Schema(description = "ID do projeto", example = "1")
    private Integer idProjeto;

    @Schema(description = "Responsável pelo projeto", example = "João Silva")
    private UsuarioSprintResponseDto responsavel;

    @Schema(description = "Indica se há impedimento no projeto", example = "false")
    private boolean comImpedimento;

    @Schema(description = "Progresso atual do projeto, em porcentagem", example = "75.5")
    private Double progresso;

    @Schema(description = "Valor do orçamento do projeto", example = "1000000.00")
    private Double orcamento;

    @Schema(description = "Lista de áreas envolvidas no projeto", implementation = Area.class)
    private List<Area> areas;

    @Schema(description = "Lista que contem número de tarefas por usuário dentro do projetoa", implementation = Area.class)
    private List<TarefaUsuario> usuarios;

    @Schema(description = "Lista de informações financeiras do projeto", implementation = InvestimentoResponseDto.class)
    private List<InvestimentoResponseDto> financeiroResponseDtos;

    @Schema(description = "Total de itens no projeto", example = "10")
    private Integer totalItens;
}
