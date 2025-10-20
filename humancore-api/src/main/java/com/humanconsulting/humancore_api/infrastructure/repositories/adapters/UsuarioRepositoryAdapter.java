package com.humanconsulting.humancore_api.infrastructure.repositories.adapters;

import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.infrastructure.entities.UsuarioEntity;
import com.humanconsulting.humancore_api.infrastructure.mappers.TarefaMapper;
import com.humanconsulting.humancore_api.infrastructure.mappers.UsuarioMapper;
import com.humanconsulting.humancore_api.infrastructure.repositories.jpa.JpaUsuarioRepository;

import java.util.List;
import java.util.Optional;

public class UsuarioRepositoryAdapter implements UsuarioRepository {

    private final JpaUsuarioRepository jpaUsuarioRepository;

    public UsuarioRepositoryAdapter(JpaUsuarioRepository jpaUsuarioRepository) {
        this.jpaUsuarioRepository = jpaUsuarioRepository;
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        return jpaUsuarioRepository.findByEmail(email)
                .map(UsuarioMapper::toDomain);
    }

    @Override
    public Usuario save(Usuario usuario) {
        UsuarioEntity entity = UsuarioMapper.toEntity(usuario);
        UsuarioEntity saved = jpaUsuarioRepository.save(entity);
        return UsuarioMapper.toDomain(saved);
    }

    @Override
    public Optional<Usuario> findById(Integer id) {
        return jpaUsuarioRepository.findById(id)
                .map(UsuarioMapper::toDomain);
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

    @Override
    public Optional<String> findDiretorByEmpresaId(Integer idEmpresa) {
        return jpaUsuarioRepository.findDiretorByEmpresaId(idEmpresa);
    }
}
