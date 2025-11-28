package com.humanconsulting.humancore_api.application.usecases.sala;

import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
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

class BuscarChatsDoUsuarioUseCaseTest {

    private SalaRepository salaRepository;
    private MensagemRepository mensagemRepository;
    private MensagemInfoRepository mensagemInfoRepository;
    private BuscarChatsDoUsuarioUseCase buscarChatsDoUsuarioUseCase;

    @BeforeEach
    void setUp() {
        salaRepository = mock(SalaRepository.class);
        mensagemRepository = mock(MensagemRepository.class);
        mensagemInfoRepository = mock(MensagemInfoRepository.class);
        buscarChatsDoUsuarioUseCase = new BuscarChatsDoUsuarioUseCase(salaRepository, mensagemRepository, mensagemInfoRepository);
    }

    @Test
    void execute_ShouldReturnChatResponse_WhenSalaHasMensagensAndInfo() {
        // Arrange
        Integer idUsuario = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setNome("Pedro");

        Sala sala = new Sala();
        sala.setIdSala(10);
        sala.setNome("Sala Teste");
        sala.setUrlImagem("img.png");
        sala.setUsuarios(Set.of(usuario));

        // Mensagem normal
        var mensagem = new com.humanconsulting.humancore_api.domain.entities.Mensagem();
        mensagem.setIdMensagem(100);
        mensagem.setSala(sala);
        mensagem.setUsuario(usuario);
        mensagem.setConteudo("Olá");
        mensagem.setHorario(LocalDateTime.now());

        // Mensagem info
        var mensagemInfo = new com.humanconsulting.humancore_api.domain.entities.MensagemInfo();
        mensagemInfo.setIdMensagemInfo(200);
        mensagemInfo.setSala(sala);
        mensagemInfo.setConteudo("Info");
        mensagemInfo.setHorario(LocalDateTime.now().plusSeconds(1));

        when(salaRepository.findSalasComUsuariosPorUsuario(idUsuario)).thenReturn(List.of(sala));
        when(mensagemRepository.findBySalaOrderByHorarioAsc(sala)).thenReturn(List.of(mensagem));
        when(mensagemInfoRepository.findBySalaOrderByHorarioAsc(sala)).thenReturn(List.of(mensagemInfo));

        // Act
        List<ChatResponseDto> result = buscarChatsDoUsuarioUseCase.execute(idUsuario);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        ChatResponseDto chat = result.get(0);
    }

    @Test
    void execute_ShouldReturnEmptyList_WhenNoSalasFound() {
        // Arrange
        when(salaRepository.findSalasComUsuariosPorUsuario(99)).thenReturn(List.of());

        // Act
        List<ChatResponseDto> result = buscarChatsDoUsuarioUseCase.execute(99);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void execute_ShouldFillEmpresa_WhenSalaHasEmpresa() {
        // Arrange
        Integer idUsuario = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setNome("Pedro");

        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(50);
        empresa.setNome("Empresa Teste");

        Sala sala = new Sala();
        sala.setIdSala(20);
        sala.setNome("Sala Empresa");
        sala.setUsuarios(Set.of(usuario));
        sala.setEmpresa(empresa);

        when(salaRepository.findSalasComUsuariosPorUsuario(idUsuario)).thenReturn(List.of(sala));
        when(mensagemRepository.findBySalaOrderByHorarioAsc(sala)).thenReturn(List.of());
        when(mensagemInfoRepository.findBySalaOrderByHorarioAsc(sala)).thenReturn(List.of());

        // Act
        List<ChatResponseDto> result = buscarChatsDoUsuarioUseCase.execute(idUsuario);

        // Assert
        assertEquals(50, result.get(0).fkEmpresa().get());
        assertEquals("Empresa Teste", result.get(0).nomeEmpresa().get());
    }

    @Test
    void execute_ShouldFillEmpresaFromProjeto_WhenSalaHasProjeto() {
        // Arrange
        Integer idUsuario = 1;
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        usuario.setNome("Pedro");

        Empresa empresa = new Empresa();
        empresa.setIdEmpresa(60);
        empresa.setNome("Empresa Projeto");

        Projeto projeto = new Projeto();
        projeto.setIdProjeto(70);
        projeto.setEmpresa(empresa);

        Sala sala = new Sala();
        sala.setIdSala(30);
        sala.setNome("Sala Projeto");
        sala.setUsuarios(Set.of(usuario));
        sala.setProjeto(projeto);

        when(salaRepository.findSalasComUsuariosPorUsuario(idUsuario)).thenReturn(List.of(sala));
        when(mensagemRepository.findBySalaOrderByHorarioAsc(sala)).thenReturn(List.of());
        when(mensagemInfoRepository.findBySalaOrderByHorarioAsc(sala)).thenReturn(List.of());

        // Act
        List<ChatResponseDto> result = buscarChatsDoUsuarioUseCase.execute(idUsuario);

        // Assert
        assertEquals(70, result.get(0).fkProjeto().get());
        assertEquals(60, result.get(0).fkEmpresa().get());
        assertEquals("Empresa Projeto", result.get(0).nomeEmpresa().get());
    }
}
