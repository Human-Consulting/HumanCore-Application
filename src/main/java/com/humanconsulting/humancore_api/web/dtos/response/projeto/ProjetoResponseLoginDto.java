package com.humanconsulting.humancore_api.web.dtos.response.projeto;

import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseLoginDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseLoginDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoResponseLoginDto {
    @Schema(description = "ID do projeto", example = "1")
    private Integer idProjeto;

    @Schema(description = "Título do projeto", example = "Centro Comercial")
    private String titulo;

    @Schema(description = "EmpresaEntity associada à TarefaEntity", example = "2")
    private EmpresaResponseLoginDto empresa;
}
