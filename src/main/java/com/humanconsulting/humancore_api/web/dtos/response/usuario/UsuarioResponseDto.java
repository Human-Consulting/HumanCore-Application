package com.humanconsulting.humancore_api.web.dtos.response.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDto {
    @Schema(description = "ID do usuário", example = "1")
    private Integer idUsuario;

    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String nome;

    @Schema(description = "Endereço de e-mail do usuário", example = "joao.silva@email.com")
    private String email;

    @Schema(description = "Cargo do usuário dentro da empresa", example = "Desenvolvedor")
    private String cargo;

    @Schema(description = "Área de atuação do usuário", example = "TI")
    private String area;

    @Schema(description = "Permissão atribuída ao usuário", example = "CONSULTOR")
    private String permissao;

    @Schema(description = "Quantidade de tarefas atribuídas ao usuário", example = "5")
    private Integer qtdTarefas;

    @Schema(description = "Indica se o usuário tem alguma tarefa com impedimento", example = "false")
    private Boolean comImpedimento;

    @Schema(description = "Cores de fundo da aplicação usuário")
    private String cores;

}
