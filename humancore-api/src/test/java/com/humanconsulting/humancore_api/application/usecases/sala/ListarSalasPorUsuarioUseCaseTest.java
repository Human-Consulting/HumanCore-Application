package com.humanconsulting.humancore_api.application.usecases.sala;

import com.humanconsulting.humancore_api.domain.entities.*;
import com.humanconsulting.humancore_api.domain.repositories.MensagemInfoRepository;
import com.humanconsulting.humancore_api.domain.repositories.MensagemRepository;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.web.dtos.response.chat.ChatResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ListarSalasPorUsuarioUseCaseTest {

    private SalaRepository salaRepository;
    private MensagemRepository mensagemRepository;
    private MensagemInfoRepository mensagemInfoRepository;
    private ListarSalasPorUsuarioUseCase useCase;

    @BeforeEach
    void setUp() {
        salaRepository = mock(SalaRepository.class);
        mensagemRepository = mock(MensagemRepository.class);
        mensagemInfoRepository = mock(MensagemInfoRepository.class);
        useCase = new ListarSalasPorUsuarioUseCase(salaRepository, mensagemRepository, mensagemInfoRepository);
    }

    @Test
    void execute_ShouldReturnChatResponse_WhenSalaHasMensagens() {
        Integer idUsuario = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setNome("Pedro");

        Sala sala = new Sala();
        sala.setIdSala(10);
        sala.setNome("Sala Teste");
        sala.setUsuarios(Set.of(usuario));

        Mensagem mensagem = new Mensagem();
        mensagem.setIdMensagem(100);
        mensagem.setSala(sala);
        mensagem.setUsuario(usuario);
        mensagem.setConteudo("Olá");
        mensagem.setHorario(LocalDateTime.now());

        MensagemInfo mensagemInfo = new MensagemInfo();
        mensagemInfo.setIdMensagemInfo(200);
        mensagemInfo.setSala(sala);
        mensagemInfo.setConteudo("Info");
        mensagemInfo.setHorario(LocalDateTime.now().plusSeconds(1));

        when(salaRepository.findSalasComUsuariosPorUsuario(idUsuario)).thenReturn(List.of(sala));
        when(mensagemRepository.findBySalaOrderByHorarioAsc(sala)).thenReturn(List.of(mensagem));
        when(mensagemInfoRepository.findBySalaOrderByHorarioAsc(sala)).thenReturn(List.of(mensagemInfo));

        List<ChatResponseDto> result = useCase.execute(idUsuario);

        assertEquals(1, result.size());
        ChatResponseDto chat = result.get(0);
    }

    @Test
    void execute_ShouldReturnEmptyList_WhenNoSalasFound() {
        when(salaRepository.findSalasComUsuariosPorUsuario(99)).thenReturn(List.of());
        List<ChatResponseDto> result = useCase.execute(99);
        assertTrue(result.isEmpty());
    }
}
