package com.humanconsulting.humancore_api.web.dtos.atualizar.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAtualizarCoresDto {

    @Schema(description = "Cor do fundo do usuário ", example = "#606080|#8d7dca|#4e5e8c|true")
    @NotBlank
    private String cores;
}
