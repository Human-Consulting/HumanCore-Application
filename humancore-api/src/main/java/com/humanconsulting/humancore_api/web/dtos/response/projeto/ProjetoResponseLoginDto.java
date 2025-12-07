package com.humanconsulting.humancore_api.web.dtos.response.projeto;

import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseLoginDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjetoResponseLoginDto implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "ID do projeto", example = "1")
    private Integer idProjeto;

    @Schema(description = "Título do projeto", example = "Centro Comercial")
    private String titulo;

    @Schema(description = "EmpresaEntity associada à TarefaEntity", example = "2")
    private EmpresaResponseLoginDto empresa;
}
