package com.humanconsulting.humancore_api.velho.service;

import com.humanconsulting.humancore_api.velho.controller.dto.token.UsuarioDetalhesDto;
import com.humanconsulting.humancore_api.velho.model.Usuario;
import com.humanconsulting.humancore_api.velho.repository.UsuarioRepository;
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
        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(username);

        if (usuarioOptional == null) throw new UsernameNotFoundException(String.format("Usuário: %s não encontrado", username));

        return new UsuarioDetalhesDto(usuarioOptional.get());
    }
}
