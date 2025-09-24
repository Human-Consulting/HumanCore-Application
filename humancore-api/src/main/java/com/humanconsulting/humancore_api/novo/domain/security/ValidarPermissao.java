package com.humanconsulting.humancore_api.novo.domain.security;

import com.humanconsulting.humancore_api.velho.enums.PermissaoEnum;
import com.humanconsulting.humancore_api.velho.exception.AcessoNegadoException;

public class ValidarPermissao {

    public static void execute(String permissaoEditor, String acao) {
        PermissaoEnum permissao;
        try {
            permissao = PermissaoEnum.valueOf(permissaoEditor);
        } catch (IllegalArgumentException e) {
            throw new AcessoNegadoException("Permissão inválida informada.");
        }

        if (!permissao.temPermissao(acao)) {
            throw new AcessoNegadoException("Você não tem permissão para editar este tipo de usuário.");
        }
    }
}
