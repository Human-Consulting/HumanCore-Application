package com.humanconsulting.humancore_api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @Schema(description = "Endereço de email do usuário", example = "usuario@exemplo.com")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "Senha do usuário", example = "senha123")
    @NotBlank
    private String senha;
}
