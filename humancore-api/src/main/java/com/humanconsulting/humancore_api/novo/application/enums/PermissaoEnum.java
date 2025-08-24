package com.humanconsulting.humancore_api.novo.application.enums;

import java.util.Arrays;
import java.util.List;

public enum PermissaoEnum {
    DIRETOR(
            Arrays.asList(
                    //USUARIO
                    "MODIFICAR_PROPRIO",
                    "MODIFICAR_GESTOR",
                    "MODIFICAR_FUNC",
                    "EDITAR_USUARIOS_EMPRESA",
                    "EXCLUIR_USUARIOS_EMPRESA",
                    "ADICIONAR_USUARIOS_EMPRESA",
                    //EMPRESA
                    //PROJETO
                    "MODIFICAR_PROJETO",
                    //SPRINT
                    "ADICIONAR_SPRINT",
                    "MODIFICAR_SPRINT",
                    "EXCLUIR_SPRINT",
                    //TAREFA
                    "ADICIONAR_TAREFA",
                    "MODIFICAR_TAREFA",
                    "EXCLUIR_TAREFA",
                    //INVESTIMENTO
                    "ADICIONAR_INVESTIMENTO",
                    "MODIFICAR_INVESTIMENTO",
                    "EXCLUIR_INVESTIMENTO"
            )
    ),

    GESTOR(
            Arrays.asList(
                    //USUARIO
                    "MODIFICAR_PROPRIO",
                    "MODIFICAR_FUNC",
                    "EDITAR_USUARIOS_EMPRESA_EXCETO_DIRETOR",
                    "EXCLUIR_USUARIOS_EMPRESA_EXCETO_DIRETOR",
                    "ADICIONAR_USUARIOS_EMPRESA_EXCETO_DIRETOR",
                    //EMPRESA
                    //PROJETO
                    "MODIFICAR_PROJETO",
                    //SPRINT
                    "ADICIONAR_SPRINT",
                    "MODIFICAR_SPRINT",
                    "EXCLUIR_SPRINT",
                    //TAREFA
                    "ADICIONAR_TAREFA",
                    "MODIFICAR_TAREFA",
                    "EXCLUIR_TAREFA",
                    //INVESTIMENTO
                    "ADICIONAR_INVESTIMENTO",
                    "MODIFICAR_INVESTIMENTO",
                    "EXCLUIR_INVESTIMENTO"
            )
    ),

    FUNC(
            Arrays.asList(
                    //USUARIO
                    "MODIFICAR_PROPRIO",
                    "MODIFICAR_PROPRIO_POR_GESTOR",
                    "MODIFICAR_PROPRIO_POR_DIRETOR",
                    //EMPRESA
                    //PROJETO
                    //TAREFA
                    "MODIFICAR_TAREFA"
                    //INVESTIMENTO
            )
    ),

    CONSULTOR_DIRETOR(
            Arrays.asList(
                    //USUARIO
                    "MODIFICAR_PROPRIO",
                    "MODIFICAR_GESTOR",
                    "MODIFICAR_FUNC",
                    "EDITAR_USUARIOS_EMPRESA",
                    "EXCLUIR_USUARIOS_EMPRESA",
                    "ADICIONAR_USUARIOS_EMPRESA",
                    //Empresa
                    "ADICIONAR_EMPRESA",
                    "MODIFICAR_EMPRESA",
                    "EXCLUIR_EMPRESA",
                    //PROJETO
                    "ADICIONAR_PROJETO",
                    "MODIFICAR_PROJETO",
                    "EXCLUIR_PROJETO",
                    //SPRINT
                    "ADICIONAR_SPRINT",
                    "MODIFICAR_SPRINT",
                    "EXCLUIR_SPRINT",
                    //TAREFA
                    "ADICIONAR_TAREFA",
                    "MODIFICAR_TAREFA",
                    "EXCLUIR_TAREFA"
                    //INVESTIMENTO
            )
    ),

    CONSULTOR(
            Arrays.asList(
                    //USUARIO
                    "MODIFICAR_PROPRIO",
                    "MODIFICAR_PROPRIO_POR_DIRETOR",
                    "EDITAR_USUARIOS_EMPRESA_EXCETO_DIRETOR",
                    "EXCLUIR_USUARIOS_EMPRESA_EXCETO_DIRETOR",
                    "ADICIONAR_USUARIOS_EMPRESA_EXCETO_DIRETOR",
                    //Empresa
                    "ADICIONAR_EMPRESA",
                    "MODIFICAR_EMPRESA",
                    "EXCLUIR_EMPRESA",
                    //PROJETO
                    "ADICIONAR_PROJETO",
                    "MODIFICAR_PROJETO",
                    "EXCLUIR_PROJETO",
                    //SPRINT
                    "ADICIONAR_SPRINT",
                    "MODIFICAR_SPRINT",
                    "EXCLUIR_SPRINT",
                    //TAREFA
                    "ADICIONAR_TAREFA",
                    "MODIFICAR_TAREFA",
                    "EXCLUIR_TAREFA"
                    //INVESTIMENTO
            )
    );

    private final List<String> acoesPermitidas;

    PermissaoEnum(List<String> acoesPermitidas) {
        this.acoesPermitidas = acoesPermitidas;
    }

    public List<String> getAcoesPermitidas() {
        return acoesPermitidas;
    }

    public boolean temPermissao(String acao) {
        return acoesPermitidas.contains(acao);
    }
}
