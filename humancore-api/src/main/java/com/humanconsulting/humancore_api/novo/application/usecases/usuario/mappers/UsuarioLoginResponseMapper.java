package com.humanconsulting.humancore_api.novo.application.usecases.usuario.mappers;

import com.humanconsulting.humancore_api.novo.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.UsuarioMapper;

import java.util.ArrayList;
import java.util.List;

public class UsuarioLoginResponseMapper {
    private final UsuarioRepository usuarioRepository;
    private final TarefaResponseMapper tarefaResponseMapper;

    public UsuarioLoginResponseMapper(UsuarioRepository usuarioRepository, TarefaResponseMapper tarefaResponseMapper) {
        this.usuarioRepository = usuarioRepository;
        this.tarefaResponseMapper = tarefaResponseMapper;
    }

    public LoginResponseDto toLoginResponse(Usuario usuario, String tokenUsuario) {
        String nomeEmpresa = usuario.getEmpresa().getNome();
        Boolean comImpedimento = usuarioRepository.hasTarefasComImpedimento(usuario.getIdUsuario());
        List<Integer> projetosVinculados = usuarioRepository.findProjetosVinculados(usuario.getIdUsuario());
        List<Tarefa> tarefasVinculadas = usuarioRepository.findTarefasVinculadas(usuario.getIdUsuario());
        List<TarefaResponseDto> tarefasResponse = new ArrayList<>();
        for (Tarefa tarefa : tarefasVinculadas) {
            TarefaResponseDto novaTarefa = tarefaResponseMapper.toResponse(tarefa);
            if (novaTarefa.getProgresso() < 100)
                tarefasResponse.add(novaTarefa);
        }
        Integer qtdTarefas = tarefasResponse.size();
        return UsuarioMapper.toLoginDto(usuario, nomeEmpresa, qtdTarefas, comImpedimento, projetosVinculados, tarefasResponse, tokenUsuario);
    }
}

