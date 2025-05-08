package com.humanconsulting.humancore_api.controller.dto.response.usuario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDto {
    @Schema(description = "ID do usuário", example = "1")
    private Integer idUsuario;

    @Schema(description = "Nome completo do usuário", example = "João Silva")
    private String nome;

    @Schema(description = "Endereço de e-mail do usuário", example = "joao.silva@email.com")
    private String email;

    @Schema(description = "Senha do usuário", example = "senha123")
    private String senha;

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

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    public Integer getQtdTarefas() {
        return qtdTarefas;
    }

    public void setQtdTarefas(Integer qtdTarefas) {
        this.qtdTarefas = qtdTarefas;
    }

    public Boolean getComImpedimento() {
        return comImpedimento;
    }

    public void setComImpedimento(Boolean comImpedimento) {
        this.comImpedimento = comImpedimento;
    }
}
