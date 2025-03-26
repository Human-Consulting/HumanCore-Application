package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario cadastrar(Usuario usuario) {
        repository.existsByEmail(usuario.getEmail());
        usuario.setIdUsuario(null);
        return repository.insert(usuario);
    }

    public Usuario buscarPorId(Integer id) {
        return repository.selectWhereId(id);
    }

    public List<Usuario> listar() {
        List<Usuario> all = repository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma empresa registrada");
        return all;
    }

    public void deletar(Integer id) {
        repository.deleteWhere(id);
    }

    public Usuario atualizarPorId(Integer id, Usuario usuario) {
        Usuario usuarioAtualizado = repository.selectWhereId(id);

        if((usuarioAtualizado != null) && (usuarioAtualizado.getIdUsuario() == id)) {
            usuarioAtualizado.setIdUsuario(id);

            repository.insert(usuario);

            return usuario;
        }

        throw new EntidadeSemRetornoException("Usuário não encontrado");
    }
}
