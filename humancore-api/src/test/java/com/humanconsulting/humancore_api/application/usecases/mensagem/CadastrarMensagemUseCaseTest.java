package com.humanconsulting.humancore_api.application.usecases.mensagem;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.humanconsulting.humancore_api.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.web.dtos.request.MensagemRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatMensagemUnificadaDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class CadastrarMensagemUseCaseTest {

    @Mock
    private MensagemRepository mensagemRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private SalaNotifier salaNotifier;

    @InjectMocks
    private CadastrarMensagemUseCase cadastrarMensagemUseCase;

    @Test
    void deveCadastrarMensagemComSucesso() {
        // Arrange
        Integer fkUsuario = 101;
        Integer fkSala = 201;
        String conteudo = "Nova mensagem";

        MensagemRequestDto requestDto = mock(MensagemRequestDto.class);
        when(requestDto.getFkUsuario()).thenReturn(fkUsuario);
        when(requestDto.getFkSala()).thenReturn(fkSala);
        when(requestDto.getConteudo()).thenReturn(conteudo);

        // Simula usuário e sala encontrados
        Usuario usuario = mock(Usuario.class);
        usuario.setIdUsuario(fkUsuario);

        Sala sala = mock(Sala.class);

        when(usuarioRepository.findById(fkUsuario)).thenReturn(Optional.of(usuario));
        when(salaRepository.findById(fkSala)).thenReturn(Optional.of(sala));

        // Simula mensagem salva com usuário e sala associados
        Mensagem mensagem = new Mensagem();
        mensagem.setIdMensagem(1);
        mensagem.setConteudo(conteudo);
        mensagem.setUsuario(usuario);
        mensagem.setSala(sala);

        when(mensagemRepository.save(any(Mensagem.class))).thenReturn(mensagem);

        // Act
        ChatMensagemUnificadaDto resultado = cadastrarMensagemUseCase.execute(requestDto);

        // Assert
        assertEquals(mensagem.getIdMensagem(), resultado.getIdMensagem());
        assertEquals(mensagem.getConteudo(), resultado.getConteudo());
        verify(usuarioRepository, times(1)).findById(fkUsuario);
        verify(salaRepository, times(1)).findById(fkSala);
        verify(mensagemRepository, times(1)).save(any(Mensagem.class));
        verify(salaNotifier, times(1)).enviarMensagem(resultado);
    }

    @Test
        void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
            // Arrange
            Integer fkUsuarioInvalido = 999;
            Integer fkSala = 201;

            MensagemRequestDto requestDto = new MensagemRequestDto();
            requestDto.setFkUsuario(fkUsuarioInvalido);
            requestDto.setFkSala(fkSala);
            requestDto.setConteudo("Mensagem inválida");

            when(usuarioRepository.findById(fkUsuarioInvalido)).thenReturn(Optional.empty());

            // Act & Assert
            assertThrows(java.util.NoSuchElementException.class, () -> {
                cadastrarMensagemUseCase.execute(requestDto);
            });

            verify(usuarioRepository, times(1)).findById(fkUsuarioInvalido);
            verify(salaRepository, never()).findById(anyInt());
            verify(mensagemRepository, never()).save(any());
            verify(salaNotifier, never()).enviarMensagem(any());
        }
}

