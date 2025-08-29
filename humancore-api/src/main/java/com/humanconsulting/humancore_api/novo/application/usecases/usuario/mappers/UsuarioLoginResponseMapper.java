package com.humanconsulting.humancore_api.novo.application.usecases.usuario.mappers;

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
    private final TarefaService tarefaService; // Isso vai virar um use case a ser importado aqui

    public UsuarioLoginResponseMapper(UsuarioRepository usuarioRepository, TarefaService tarefaService) {
        this.usuarioRepository = usuarioRepository;
        this.tarefaService = tarefaService;
    }

    public LoginResponseDto toLoginResponse(Usuario usuario, String tokenUsuario) {
        String nomeEmpresa = usuario.getEmpresa().getNome();
        Boolean comImpedimento = usuarioRepository.hasTarefasComImpedimento(usuario.getIdUsuario());
        List<Integer> projetosVinculados = usuarioRepository.findProjetosVinculados(usuario.getIdUsuario());
        List<Tarefa> tarefasVinculadas = usuarioRepository.findTarefasVinculadas(usuario.getIdUsuario());
        List<TarefaResponseDto> tarefasResponse = new ArrayList<>();
        for (Tarefa tarefa : tarefasVinculadas) {
            TarefaResponseDto novaTarefa = tarefaService.passarParaResponse(tarefa);
            if (novaTarefa.getProgresso() < 100)
                tarefasResponse.add(novaTarefa);
        }
        Integer qtdTarefas = tarefasResponse.size();
        return UsuarioMapper.toLoginDto(usuario, nomeEmpresa, qtdTarefas, comImpedimento, projetosVinculados, tarefasResponse, tokenUsuario);
    }
}

