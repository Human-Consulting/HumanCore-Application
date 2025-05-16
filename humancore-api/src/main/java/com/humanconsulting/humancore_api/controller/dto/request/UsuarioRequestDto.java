package com.humanconsulting.humancore_api.controller.dto.request;

import com.humanconsulting.humancore_api.model.Empresa;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class UsuarioRequestDto {
    @Schema(description = "Nome do usuário", example = "João Silva")
    @NotBlank
    private String nome;

    @Schema(description = "Email do usuário", example = "joao.silva@example.com")
    @NotNull
    @Email
    private String email;

    @Schema(description = "Senha do usuário", example = "senha123")
    @NotBlank
    @Size(min = 6)
    private String senha;

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
