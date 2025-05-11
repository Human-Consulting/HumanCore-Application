package com.humanconsulting.humancore_api.controller.dto.notificar;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificacaoEmailDto {
    @NotBlank
    private String nomeResponsavel;
    @NotBlank
    private List<String> emails;
    @NotNull
    @NotBlank
    private String mensagem;
}
