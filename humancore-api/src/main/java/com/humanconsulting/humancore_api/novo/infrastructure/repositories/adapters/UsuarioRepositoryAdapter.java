package com.humanconsulting.humancore_api.novo.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.novo.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.infrastructure.entities.UsuarioEntity;
import com.humanconsulting.humancore_api.novo.infrastructure.mappers.TarefaMapper;
import com.humanconsulting.humancore_api.novo.infrastructure.mappers.UsuarioMapper;
import com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa.JpaUsuarioRepository;

import java.util.List;

public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final JpaUsuarioRepository jpaUsuarioRepository;

    public UsuarioRepositoryAdapter(JpaUsuarioRepository jpaUsuarioRepository) {
        this.jpaUsuarioRepository = jpaUsuarioRepository;
    }

    @Override
    public Usuario findByEmail(String email) {
        return jpaUsuarioRepository.findByEmail(email)
                .map(UsuarioMapper::toDomain)
                .orElse(null);
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = UsuarioMapper.toEntity(usuario);
        UsuarioEntity saved = jpaUsuarioRepository.save(entity);
        return UsuarioMapper.toDomain(saved);
    }

    @Override
    public Usuario findById(Integer id) {
        return jpaUsuarioRepository.findById(id)
                .map(UsuarioMapper::toDomain)
                .orElse(null);
    }

    @Override
    public List<Usuario> findAll() {
        return jpaUsuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDomain)
                .toList();
    }

    @Override
    public void deleteById(Integer id) {
        jpaUsuarioRepository.deleteById(id);
    }

    @Override
    public List<Usuario> findByFkEmpresa(Integer idEmpresa) {
        return jpaUsuarioRepository.findByFkEmpresa(idEmpresa)
                .stream()
                .map(UsuarioMapper::toDomain)
                .toList();
    }

    @Override
    public Integer countTarefasByUsuario(Integer idUsuario) {
        return jpaUsuarioRepository.countTarefasByUsuario(idUsuario);
    }

    @Override
    public boolean hasTarefasComImpedimento(Integer idUsuario) {
        return jpaUsuarioRepository.hasTarefaComImpedimento(idUsuario);
    }

    @Override
    public List<Integer> findProjetosVinculados(Integer idUsuario) {
        return jpaUsuarioRepository.findProjetosVinculados(idUsuario);
    }

    @Override
    public List<Tarefa> findTarefasVinculadas(Integer idUsuario) {
        return jpaUsuarioRepository.findTarefasVinculadas(idUsuario)
                .stream()
                .map(TarefaMapper::toDomain)
                .toList();
    }
}
