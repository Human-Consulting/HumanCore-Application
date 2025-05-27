package com.humanconsulting.humancore_api.controller.dto.response.mensagem;

import com.humanconsulting.humancore_api.model.Sala;
import com.humanconsulting.humancore_api.model.Usuario;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MensagemResponseDto {
    @Schema(description = "ID da mensagem", example = "1")
    private Integer idMensagem;

    @Schema(description = "Conteúdo da mensagem", example = "Oi, tudo bem?")
    private String conteudo;

    @Schema(description = "Horário da mensagem", example = "2025-05-24 20:20")
    private LocalDateTime horario;

    @Schema(description = "Usuário associado à mensagem", example = "1")
    @NotNull
    private Integer fkUsuario;

    @Schema(description = "Sala associada à mensagem", example = "1")
    @NotNull
    private Integer fkSala;
}
