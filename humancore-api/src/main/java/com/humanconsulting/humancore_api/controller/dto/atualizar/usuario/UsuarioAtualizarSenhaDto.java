package com.humanconsulting.humancore_api.controller.dto.atualizar.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioAtualizarSenhaDto {

    @Schema(description = "Senha do usuário (mínimo de 6 caracteres)", example = "senha123")
    @NotBlank
    @Size(min = 6)
    private String senhaAtual;

    @Schema(description = "Nova senha do usuário (mínimo de 6 caracteres)", example = "senha321")
    @NotBlank
    @Size(min = 6)
    private String senhaAtualizada;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;
}
