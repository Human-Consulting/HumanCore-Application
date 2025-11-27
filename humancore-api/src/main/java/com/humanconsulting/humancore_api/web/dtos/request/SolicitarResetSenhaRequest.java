package com.humanconsulting.humancore_api.web.dtos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SolicitarResetSenhaRequest {
    @NotNull
    @Email
    private String email;
}

