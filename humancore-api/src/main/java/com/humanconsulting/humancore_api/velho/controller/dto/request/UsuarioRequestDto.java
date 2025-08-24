package com.humanconsulting.humancore_api.velho.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDto {
    @Schema(description = "Nome do usuário", example = "João Silva")
    @NotBlank
    private String nome;

    @Schema(description = "Email do usuário", example = "joao.silva@example.com")
    @NotNull
    @Email
    private String email;

    @Schema(description = "Cargo do usuário na empresa", example = "Desenvolvedor Backend")
    @NotBlank
    private String cargo;

    @Schema(description = "Área de atuação do usuário", example = "Tecnologia da Informação")
    @NotBlank
    private String area;

    @Schema(description = "Permissão do usuário", example = "CONSULTOR")
    @NotBlank
    private String permissao;

    @Schema(description = "ID da empresa associada ao usuário", example = "1")
    @NotNull
    private Integer fkEmpresa;
}
