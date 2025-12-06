package com.humanconsulting.humancore_api.web.dtos.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResetSenhaRequest {
    @NotNull
    private String token;

    @NotNull
    private String senha;
}

