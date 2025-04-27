package com.humanconsulting.humancore_api.enums;

import java.util.Arrays;
import java.util.List;

public enum PermissaoEnum {
    DIRETOR(
            Arrays.asList(
                    "MODIFICAR_PROPRIO",
                    "MODIFICAR_GESTOR",
                    "MODIFICAR_FUNC",
                    "EDITAR_USUARIOS_EMPRESA",
                    "EXCLUIR_USUARIOS_EMPRESA",
                    "ADICIONAR_USUARIOS_EMPRESA"
            )
    ),

    GESTOR(
            Arrays.asList(
                    "MODIFICAR_PROPRIO",
                    "MODIFICAR_FUNC",
                    "EDITAR_USUARIOS_EMPRESA_EXCETO_DIRETOR",
                    "EXCLUIR_USUARIOS_EMPRESA_EXCETO_DIRETOR",
                    "ADICIONAR_USUARIOS_EMPRESA_EXCETO_DIRETOR"
            )
    ),

    FUNC(
            Arrays.asList(
                    "MODIFICAR_PROPRIO",
                    "MODIFICAR_PROPRIO_POR_GESTOR",
                    "MODIFICAR_PROPRIO_POR_DIRETOR"
            )
    ),

    CONSULTOR_DIRETOR(
            Arrays.asList(
                    "MODIFICAR_PROPRIO",
                    "EDITAR_USUARIOS_EMPRESA",
                    "EXCLUIR_USUARIOS_EMPRESA",
                    "ADICIONAR_USUARIOS_EMPRESA"
            )
    ),

    CONSULTOR(
            Arrays.asList(
                    "MODIFICAR_PROPRIO",
                    "MODIFICAR_PROPRIO_POR_DIRETOR",
                    "EDITAR_USUARIOS_EMPRESA_EXCETO_DIRETOR",
                    "EXCLUIR_USUARIOS_EMPRESA_EXCETO_DIRETOR",
                    "ADICIONAR_USUARIOS_EMPRESA_EXCETO_DIRETOR"
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
