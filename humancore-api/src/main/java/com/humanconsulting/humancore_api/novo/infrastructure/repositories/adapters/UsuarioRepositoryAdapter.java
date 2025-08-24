package com.humanconsulting.humancore_api.novo.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa.JpaUsuarioRepository;

public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final JpaUsuarioRepository jpaUsuarioRepository;

    public UsuarioRepositoryAdapter(JpaUsuarioRepository jpaUsuarioRepository) {
        this.jpaUsuarioRepository = jpaUsuarioRepository;
    }

    @Override
    public Usuario findByEmail(String email) {
        return null;
    }

    @Override
    public Usuario save(Usuario usuario) {
        return null;
    }

    @Override
    public Usuario findById(Integer id) {
        return null;
    }

    @Override
    public Usuario findAll() {
        return null;
    }

    @Override
    public Usuario deleteById(Integer id) {
        return null;
    }

    @Override
    public Usuario findByFkEmpresa(Integer id) {
        return null;
    }

    @Override
    public Usuario countTarefasByUsuario(Integer id) {
        return null;
    }

    @Override
    public Usuario hasTarefasComImpedimento(Integer id) {
        return null;
    }

    @Override
    public Usuario findProjetosVinculados(Integer id) {
        return null;
    }

    @Override
    public Usuario findTarefasVinculadas(Integer id) {
        return null;
    }
}
