package com.humanconsulting.humancore_api.application.usecases.mensagem;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.humanconsulting.humancore_api.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.web.dtos.atualizar.mensagem.MensagemAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatMensagemUnificadaDto;
import com.humanconsulting.humancore_api.web.dtos.response.mensagem.MensagemResponseDto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.web.mappers.MensagemMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class AtualizarMensagemUseCaseTest {

    @Mock
    private MensagemRepository mensagemRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private SalaRepository salaRepository;

    @Mock
    private BuscarMensagemPorIdUseCase buscarMensagemPorIdUseCase;

    @InjectMocks
    private AtualizarMensagemUseCase atualizarMensagemUseCase;

    @Test
    void deveAtualizarMensagemComSucesso() {
        // Arrange
        Integer idMensagem = 1;
        Integer fkUsuario = 101;
        Integer fkSala = 201;
        String conteudo = "Nova mensagem atualizada";

        MensagemAtualizarRequestDto requestDto = new MensagemAtualizarRequestDto();
        requestDto.setFkUsuario(fkUsuario);
        requestDto.setFkSala(fkSala);
        requestDto.setConteudo(conteudo);

        MensagemResponseDto mensagemOriginal = new MensagemResponseDto();
        mensagemOriginal.setIdMensagem(idMensagem);

        when(buscarMensagemPorIdUseCase.execute(idMensagem)).thenReturn(mensagemOriginal);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(fkUsuario);

        Sala sala = new Sala();
        sala.setIdSala(fkSala);

        when(usuarioRepository.findById(fkUsuario)).thenReturn(Optional.of(usuario));
        when(salaRepository.findById(fkSala)).thenReturn(Optional.of(sala));

        Mensagem mensagemAtualizada = new Mensagem();

        mensagemAtualizada.setIdMensagem(idMensagem);
        mensagemAtualizada.setConteudo(conteudo);
        mensagemAtualizada.setSala(sala);
        mensagemAtualizada.setUsuario(usuario);

        when(mensagemRepository.save(any(Mensagem.class))).thenReturn(mensagemAtualizada);

        ChatMensagemUnificadaDto expectedResponse = mock(ChatMensagemUnificadaDto.class);

        MensagemMapper.toMensagemUnificadaResponse(mensagemAtualizada);

        // Act
        atualizarMensagemUseCase.execute(idMensagem, requestDto);

        // Assert
        verify(usuarioRepository, times(1)).findById(fkUsuario);
        verify(salaRepository, times(1)).findById(fkSala);
        verify(mensagemRepository, times(1)).save(any(Mensagem.class));
    }

    @Test
    void deveLancarExcecaoQuandoUsuarioNaoEncontrado() {
        // Arrange
        Integer idMensagem = 1;
        Integer fkUsuario = 101;
        Integer fkSala = 201;

        MensagemAtualizarRequestDto requestDto = new MensagemAtualizarRequestDto();
        requestDto.setFkUsuario(fkUsuario);
        requestDto.setFkSala(fkSala);

        MensagemResponseDto mensagemOriginal = new MensagemResponseDto();
        mensagemOriginal.setIdMensagem(idMensagem);

        when(buscarMensagemPorIdUseCase.execute(idMensagem)).thenReturn(mensagemOriginal);
        when(usuarioRepository.findById(fkUsuario)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            atualizarMensagemUseCase.execute(idMensagem, requestDto);
        });

        verify(usuarioRepository, times(1)).findById(fkUsuario);
        verify(salaRepository, never()).findById(anyInt());
        verify(mensagemRepository, never()).save(any(Mensagem.class));
    }

    @Test
    void deveLancarExcecaoQuandoSalaNaoEncontrada() {
        // Arrange
        Integer idMensagem = 1;
        Integer fkUsuario = 101;
        Integer fkSala = 201;

        MensagemAtualizarRequestDto requestDto = new MensagemAtualizarRequestDto();
        requestDto.setFkUsuario(fkUsuario);
        requestDto.setFkSala(fkSala);

        MensagemResponseDto mensagemOriginal = new MensagemResponseDto();
        mensagemOriginal.setIdMensagem(idMensagem);

        when(buscarMensagemPorIdUseCase.execute(idMensagem)).thenReturn(mensagemOriginal);
        when(usuarioRepository.findById(fkUsuario)).thenReturn(Optional.of(new Usuario()));
        when(salaRepository.findById(fkSala)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntidadeNaoEncontradaException.class, () -> {
            atualizarMensagemUseCase.execute(idMensagem, requestDto);
        });

        verify(usuarioRepository, times(1)).findById(fkUsuario);
        verify(salaRepository, times(1)).findById(fkSala);
        verify(mensagemRepository, never()).save(any(Mensagem.class));
    }
}
