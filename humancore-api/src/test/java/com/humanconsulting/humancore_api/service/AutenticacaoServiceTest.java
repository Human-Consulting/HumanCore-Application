package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.token.UsuarioDetalhesDto;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AutenticacaoServiceTest {

    private UsuarioRepository usuarioRepository;
    private AutenticacaoService autenticacaoService;

    @BeforeEach
    void setUp() {
        usuarioRepository = mock(UsuarioRepository.class);
        autenticacaoService = new AutenticacaoService();
        autenticacaoService.usuarioRepository = usuarioRepository;
    }

    @Test
    void deveRetornarUserDetailsQuandoUsuarioExiste() {
        Usuario usuario = new Usuario();
        usuario.setEmail("teste@exemplo.com");
        when(usuarioRepository.findByEmail("teste@exemplo.com")).thenReturn(Optional.of(usuario));

        UserDetails userDetails = autenticacaoService.loadUserByUsername("teste@exemplo.com");

        assertNotNull(userDetails);
        assertTrue(userDetails instanceof UsuarioDetalhesDto);
    }

//    @Test
//    void deveLancarExcecaoQuandoUsuarioNaoExiste() {
//        when(usuarioRepository.findByEmail("naoexiste@exemplo.com")).thenReturn(Optional.empty());
//
//        assertThrows(UsernameNotFoundException.class, () -> {
//            autenticacaoService.loadUserByUsername("naoexiste@exemplo.com");
//        });
//    }
}