package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;

public interface UsuarioRepository {

    Usuario findByEmail(String email);

    Usuario save(Usuario usuario);

    Usuario findById(Integer id);

    Usuario findAll();

    Usuario deleteById(Integer id);

    Usuario findByFkEmpresa(Integer id);

    Usuario countTarefasByUsuario(Integer id);

    Usuario hasTarefasComImpedimento(Integer id);

    Usuario findProjetosVinculados(Integer id);

    Usuario findTarefasVinculadas(Integer id);
}
