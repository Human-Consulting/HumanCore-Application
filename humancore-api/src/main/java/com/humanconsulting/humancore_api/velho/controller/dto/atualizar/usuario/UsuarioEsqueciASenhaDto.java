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
public class UsuarioEsqueciASenhaDto {

    @Schema(description = "Nova senha do usuário (mínimo de 6 caracteres)", example = "senha321")
    @NotBlank
    @Size(min = 6)
    private String senhaAtualizada;
}
