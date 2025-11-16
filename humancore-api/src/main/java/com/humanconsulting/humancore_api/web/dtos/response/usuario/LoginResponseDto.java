package com.humanconsulting.humancore_api.web.dtos.response.usuario;

import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaLoginResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    @Schema(description = "ID do usuário", example = "1")
    private Integer idUsuario;

    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String nome;

    private String email;

    @Schema(description = "Permissão atribuída ao usuário", example = "CONSULTOR")
    private String permissao;

    @Schema(description = "Identificador da empresa do usuário", example = "5")
    private Integer idEmpresa;

    @Schema(description = "Nome da empresa à qual o usuário pertence", example = "Tech Corp")
    private String nomeEmpresa;

    @Schema(description = "Quantidade de tarefas atribuídas ao usuário", example = "3")
    private Integer qtdTarefas;

    @Schema(description = "Indica se o usuário tem alguma tarefa com impedimento", example = "false")
    private Boolean comImpedimento;

    @Schema(description = "Lista de IDs dos projetos vinculados ao usuário", example = "[1, 2, 3]")
    private List<Integer> projetosVinculados;

    @Schema(description = "Lista de tarefas vinculadas ao usuário", implementation = TarefaLoginResponseDto.class)
    private List<TarefaLoginResponseDto> tarefasVinculadas;

    @Schema(description = "Token de segurança do usuário")
    private String token;

    @Schema(description = "Cores de fundo da aplicação usuário")
    private String cores;
}
