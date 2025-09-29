package com.humanconsulting.humancore_api.web.dtos.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MensagemInfoRequestDto {
    @Schema(description = "Conteudo da mensagem", example = "Oi, tudo bem?")
    @NotBlank
    private String conteudo;

    @Schema(description = "Horário da mensagem", example = "2025-05-24 20:20")
    private LocalDateTime horario;

    @Schema(description = "SalaEntity associada à mensagem", example = "1")
    @NotNull
    private Integer fkSala;

}
