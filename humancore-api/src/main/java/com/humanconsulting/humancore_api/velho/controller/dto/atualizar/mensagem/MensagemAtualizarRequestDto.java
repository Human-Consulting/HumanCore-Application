package com.humanconsulting.humancore_api.velho.controller.dto.atualizar.mensagem;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MensagemAtualizarRequestDto {
    @Schema(description = "Conteudo da mensagem", example = "Oi, tudo bem?")
    @NotBlank
    private String conteudo;

    @Schema(description = "Horário da mensagem", example = "2025-05-24 20:20")
    private LocalDateTime horario;

    @Schema(description = "Usuário associado ao projeto", example = "1")
    @NotNull
    private Integer fkUsuario;

    @Schema(description = "SalaEntity associada à mensagem", example = "1")
    @NotNull
    private Integer fkSala;

    @Schema(description = "ID do usuário que está realizando a requisição (editor)", example = "2")
    @NotNull
    private Integer idEditor;

    @Schema(description = "Permissão do editor", example = "CONSULTOR")
    @NotNull
    private String permissaoEditor;
}
