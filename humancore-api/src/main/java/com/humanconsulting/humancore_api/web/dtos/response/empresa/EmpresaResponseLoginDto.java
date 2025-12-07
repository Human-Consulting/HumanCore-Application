package com.humanconsulting.humancore_api.web.dtos.response.empresa;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpresaResponseLoginDto implements Serializable {
    @java.io.Serial
    private static final long serialVersionUID = 1L;
    @Schema(description = "ID da empresa", example = "1")
    private Integer idEmpresa;

    @Schema(description = "Título da empresa", example = "Centro Comercial")
    private String nome;
}
