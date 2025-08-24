package com.humanconsulting.humancore_api.velho.controller.dto.atualizar.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
