package com.humanconsulting.humancore_api.controller.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioEnviarCodigoRequestDto {
    @Schema(description = "Email que será enviado o código", example = "joao.silva@example.com")
    @NotNull
    @Email
    private String email;

    @Schema(description = "Código que será enviado no email", example = "123456")
    @NotNull
    @Min(6)
    private String codigo;
}
