package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;

import java.util.List;

public interface UsuarioRepository {

    Usuario findByEmail(String email);

    Usuario save(Usuario usuario);

    Usuario findById(Integer id);

    List<Usuario> findAll();

    void deleteById(Integer id);

    List<Usuario> findByFkEmpresa(Integer id);

    Integer countTarefasByUsuario(Integer id);

    boolean hasTarefasComImpedimento(Integer id);

    List<Integer> findProjetosVinculados(Integer id);

    List<Tarefa> findTarefasVinculadas(Integer id);
}
