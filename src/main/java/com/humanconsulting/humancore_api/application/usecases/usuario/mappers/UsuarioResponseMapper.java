package com.humanconsulting.humancore_api.application.usecases.usuario.mappers;

import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaLoginResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.UsuarioResponseDto;
import com.humanconsulting.humancore_api.web.mappers.UsuarioMapper;

import java.util.ArrayList;
import java.util.List;

public class UsuarioResponseMapper {
    private final UsuarioRepository usuarioRepository;
    private final TarefaResponseMapper tarefaResponseMapper;

    public UsuarioResponseMapper(UsuarioRepository usuarioRepository, TarefaResponseMapper tarefaResponseMapper) {
        this.usuarioRepository = usuarioRepository;
        this.tarefaResponseMapper = tarefaResponseMapper;
    }

    public UsuarioResponseDto toResponse(Usuario usuario) {
        Integer qtdTarefas = usuarioRepository.countTarefasByUsuario(usuario.getIdUsuario());
        Boolean comImpedimento = usuarioRepository.hasTarefasComImpedimento(usuario.getIdUsuario());
        return UsuarioMapper.toUsuarioDto(usuario, qtdTarefas, comImpedimento);
    }

    public LoginResponseDto toLoginResponse(Usuario usuario, String tokenUsuario) {
        String nomeEmpresa = usuario.getEmpresa().getNome();
        Boolean comImpedimento = usuarioRepository.hasTarefasComImpedimento(usuario.getIdUsuario());
        List<Integer> projetosVinculados = usuarioRepository.findProjetosVinculados(usuario.getIdUsuario());
        List<Tarefa> tarefasVinculadas = usuarioRepository.findTarefasVinculadas(usuario.getIdUsuario());
        List<TarefaLoginResponseDto> tarefasResponse = new ArrayList<>();
        for (Tarefa tarefa : tarefasVinculadas) {
            TarefaLoginResponseDto novaTarefa = tarefaResponseMapper.toLoginResponse(tarefa);
            if (novaTarefa.getProgresso() < 100)
                tarefasResponse.add(novaTarefa);
        }
        Integer qtdTarefas = tarefasResponse.size();
        return UsuarioMapper.toLoginDto(usuario, nomeEmpresa, qtdTarefas, comImpedimento, projetosVinculados, tarefasResponse, tokenUsuario);
    }
}

