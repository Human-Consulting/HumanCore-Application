package com.humanconsulting.humancore_api.domain.repositories;

import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> findByEmail(String email);

    Usuario save(Usuario usuario);

    Optional<Usuario> findById(Integer id);

    List<Usuario> findAll();

    void deleteById(Integer id);

    List<Usuario> findByFkEmpresa(Integer id);

    Integer countTarefasByUsuario(Integer id);

    boolean hasTarefasComImpedimento(Integer id);

    List<Integer> findProjetosVinculados(Integer id);

    List<Tarefa> findTarefasVinculadas(Integer id);

    Optional<String> findDiretorByEmpresaId(Integer idEmpresa);
}
