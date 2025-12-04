package com.humanconsulting.humancore_api.application.usecases.autenticacao;

import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.token.UsuarioDetalhesDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CarregarUsuarioPorEmailUseCaseTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    CarregarUsuarioPorEmailUseCase useCase;

    @Test
    void DadoUmEmailExistenteQuandoExecuteForChamadoDeveRetornarDto () {
        String email = "teste@exemplo.com";

        Usuario usuarioMock = mock(Usuario.class);

        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.of(usuarioMock));

        UsuarioDetalhesDto dto = useCase.execute(email);

        assertNotNull(dto);
        verify(usuarioRepository, times(1)).findByEmail(email);
    }

    @Test
    void DadoUmEmailInexistenteQuandoExecuteForChamadoDeveLancarExcecao() {
        String email = "naoexiste@exemplo.com";
        when(usuarioRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> useCase.execute(email));
        verify(usuarioRepository, times(1)).findByEmail(email);
    }
}
