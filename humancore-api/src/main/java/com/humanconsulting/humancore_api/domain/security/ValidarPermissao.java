package com.humanconsulting.humancore_api.domain.security;


import com.humanconsulting.humancore_api.application.enums.PermissaoEnum;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemPermissaoException;

public class ValidarPermissao {

    public static void execute(String permissaoEditor, String acao) {
        PermissaoEnum permissao;
        try {
            permissao = PermissaoEnum.valueOf(permissaoEditor);
        } catch (IllegalArgumentException e) {
            throw new EntidadeSemPermissaoException("Permissão inválida informada.");
        }

        if (!permissao.temPermissao(acao)) {
            throw new EntidadeSemPermissaoException("Você não tem permissão para editar este tipo de usuário.");
        }
    }
}
