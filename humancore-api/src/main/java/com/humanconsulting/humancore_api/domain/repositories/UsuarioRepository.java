package com.humanconsulting.humancore_api.domain.repositories;

import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.utils.PageResult;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {

    Optional<Usuario> findByEmail(String email);

    Usuario save(Usuario usuario);

    Optional<Usuario> findById(Integer id);

    List<Usuario> findAll();

    void deleteById(Integer id);

    PageResult<Usuario> findByFkEmpresa(Integer id, int page, int size);

    PageResult<Usuario> findByFkEmpresaOrUsuarioPermissaoLikeConsultor(Integer id, int page, int size);

    PageResult<Usuario> findByFkEmpresa_IdEmpresaAndNomeContainingIgnoreCase(Integer idEmpresa, int page, int size, String nome);

    PageResult<Usuario> findByFkEmpresa_IdEmpresaOrUsuarioPermissaoLikeConsultorAndNomeContainingIgnoreCase(Integer idEmpresa, int page, int size, String nome);

    Integer countTarefasByUsuario(Integer id);

    boolean hasTarefasComImpedimento(Integer id);

    List<Integer> findProjetosVinculados(Integer id);

    List<Tarefa> findTarefasVinculadas(Integer id);

    Usuario findDiretorByEmpresaId(Integer idEmpresa);

    PageResult<Usuario> findByFkEmpresaAndPermissaoNot(Integer idEmpresa, int page, int size);
}
