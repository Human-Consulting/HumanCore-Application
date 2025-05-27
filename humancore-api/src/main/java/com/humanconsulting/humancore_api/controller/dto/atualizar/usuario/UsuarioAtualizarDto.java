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
public class UsuarioAtualizarDto {

    @Schema(description = "Nome completo do usuário", example = "João Silva")
    @NotBlank
    private String nome;

    @Schema(description = "E-mail do usuário", example = "joao.silva@email.com")
    @NotNull
    @Email
    private String email;

    @Schema(description = "Cargo do usuário na empresa", example = "Desenvolvedor")
    @NotBlank
    private String cargo;

    @Schema(description = "Área de atuação do usuário", example = "Tecnologia")
    @NotBlank
    private String area;

    @Schema(description = "Permissão atribuída ao usuário", example = "CONSULTOR")
    @NotBlank
    private String permissao;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
    @NotNull
    private String permissaoEditor;
}
