package com.humanconsulting.humancore_api.security;

import com.humanconsulting.humancore_api.enums.PermissaoEnum;
import com.humanconsulting.humancore_api.exception.AcessoNegadoException;

public class PermissaoValidator {

    public static void validarPermissao(String permissaoEditor, String acao) {

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
