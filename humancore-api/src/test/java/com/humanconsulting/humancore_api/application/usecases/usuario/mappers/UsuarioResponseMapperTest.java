package com.humanconsulting.humancore_api.application.usecases.usuario.mappers;

import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaLoginResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsuarioResponseMapperTest {

    private UsuarioRepository usuarioRepository;
    private TarefaResponseMapper tarefaResponseMapper;
    private UsuarioResponseMapper mapper;

    @BeforeEach
    void setup() {
        usuarioRepository = mock(UsuarioRepository.class);
        tarefaResponseMapper = mock(TarefaResponseMapper.class);
        mapper = new UsuarioResponseMapper(usuarioRepository, tarefaResponseMapper);
    }

    @Test
    void toResponseSucesso() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);
        usuario.setNome("Pedro");

        when(usuarioRepository.countTarefasByUsuario(1)).thenReturn(5);
        when(usuarioRepository.hasTarefasComImpedimento(1)).thenReturn(false);

        UsuarioResponseDto response = mapper.toResponse(usuario);

        assertNotNull(response);
        assertEquals(1, response.getIdUsuario());
        assertEquals("Pedro", response.getNome());
        assertEquals(5, response.getQtdTarefas());
        assertFalse(response.getComImpedimento());
    }

    @Test
    void toResponseFalha() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(2);

        when(usuarioRepository.countTarefasByUsuario(2)).thenReturn(0);
        when(usuarioRepository.hasTarefasComImpedimento(2)).thenReturn(true);

        UsuarioResponseDto response = mapper.toResponse(usuario);

        assertNotNull(response);
        assertEquals(0, response.getQtdTarefas());
        assertTrue(response.getComImpedimento());
    }

    @Test
    void toLoginResponseSucesso() {
        Empresa empresa = new Empresa();
        empresa.setNome("Empresa Teste");

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(3);
        usuario.setEmpresa(empresa);

        Tarefa tarefa1 = new Tarefa();
        tarefa1.setIdTarefa(10);

        Tarefa tarefa2 = new Tarefa();
        tarefa2.setIdTarefa(20);

        TarefaLoginResponseDto dto1 = mock(TarefaLoginResponseDto.class);

        TarefaLoginResponseDto dto2 = mock(TarefaLoginResponseDto.class);

        when(usuarioRepository.hasTarefasComImpedimento(3)).thenReturn(false);
        when(usuarioRepository.findProjetosVinculados(3)).thenReturn(List.of(100, 200));
        when(usuarioRepository.findTarefasVinculadas(3)).thenReturn(List.of(tarefa1, tarefa2));
        when(tarefaResponseMapper.toLoginResponse(tarefa1)).thenReturn(dto1);
        when(tarefaResponseMapper.toLoginResponse(tarefa2)).thenReturn(dto2);

        LoginResponseDto response = mapper.toLoginResponse(usuario, "token123");

        assertNotNull(response);
        assertEquals("Empresa Teste", response.getNomeEmpresa());
        assertEquals(2, response.getQtdTarefas());
        assertEquals("token123", response.getToken());
    }

    @Test
    void toLoginResponseFalha() {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(4);

        when(usuarioRepository.hasTarefasComImpedimento(4)).thenReturn(false);
        when(usuarioRepository.findProjetosVinculados(4)).thenReturn(Collections.emptyList());
        when(usuarioRepository.findTarefasVinculadas(4)).thenReturn(Collections.emptyList());

        // Esperado: NullPointerException porque o código acessa usuario.getEmpresa().getNome()
        assertThrows(NullPointerException.class, () -> mapper.toLoginResponse(usuario, "tokenXYZ"));
    }
}
