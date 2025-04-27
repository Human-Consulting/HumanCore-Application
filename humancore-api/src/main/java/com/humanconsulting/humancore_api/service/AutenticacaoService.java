package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.token.UsuarioDetalhesDto;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticacaoService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepository.selectWhereEmail(username);

        if (usuarioOptional.isEmpty()) throw new UsernameNotFoundException(String.format("Usuário: %s não encontrado", username));

        return new UsuarioDetalhesDto(usuarioOptional.get());
    }
}
