package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa.AtualizarStatusRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.TarefaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.TarefaResponseDto;
import com.humanconsulting.humancore_api.enums.PermissaoEnum;
import com.humanconsulting.humancore_api.exception.AcessoNegadoException;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.TarefaMapper;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.observer.EmailNotifier;
import com.humanconsulting.humancore_api.repository.TarefaRepository;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired private TarefaRepository tarefaRepository;

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired private EmailNotifier emailNotifier;

    public TarefaResponseDto cadastrar(TarefaRequestDto tarefaRequestDto) {
        if (tarefaRequestDto.getDtInicio().isAfter(tarefaRequestDto.getDtFim()) || tarefaRequestDto.getDtInicio().isEqual(tarefaRequestDto.getDtFim())) throw new EntidadeConflitanteException("Datas de início e fim conflitantes.");
        Tarefa tarefa = tarefaRepository.save(TarefaMapper.toEntity(tarefaRequestDto));
        return passarParaResponse(tarefa);
    }

    public TarefaResponseDto buscarPorId(Integer id) {
        Optional<Tarefa> optTarefa = tarefaRepository.findById(id);
        Tarefa tarefa = optTarefa.get();

        if (optTarefa.isEmpty()) throw new EntidadeNaoEncontradaException("Tarefa não encontada");

        return passarParaResponse(tarefa);
    }

    public List<TarefaResponseDto> listar() {
        List<Tarefa> all = tarefaRepository.findAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma tarefa registrada");
        List<TarefaResponseDto> allResponse = new ArrayList<>();
        for (Tarefa tarefa : all) {
            allResponse.add(passarParaResponse(tarefa));
        }
        return allResponse;
    }

    public List<TarefaResponseDto> listarPorSprint(Integer idSprint) {
        List<Tarefa> all = tarefaRepository.findBySprint_IdSprint(idSprint);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma tarefa registrada");
        List<TarefaResponseDto> allResponse = new ArrayList<>();
        for (Tarefa tarefa : all) {
            allResponse.add(passarParaResponse(tarefa));
        }
        return allResponse;
    }

    public void deletar(Integer id) {
        Optional<Tarefa> optTarefa = tarefaRepository.findById(id);

        if (optTarefa.isEmpty()) throw new EntidadeNaoEncontradaException("Tarefa não encontrada.");

        tarefaRepository.deleteById(id);
    }

    public TarefaResponseDto atualizar(Integer idEditor, Integer idTarefa, AtualizarGeralRequestDto requestUpdate) {
        Optional<Tarefa> optTarefa = tarefaRepository.findById(idTarefa);
        if (optTarefa.isEmpty()) throw new EntidadeNaoEncontradaException("Tarefa não encotrada.");

        Optional<Usuario> optUsuarioEditor = usuarioRepository.findById(idEditor);
        requestUpdate.setIdEditor(idEditor);
        Usuario usuarioEditor = optUsuarioEditor.get();
        PermissaoEnum permissaoEditor;

        try {
            permissaoEditor = PermissaoEnum.valueOf(usuarioEditor.getPermissao());
        } catch (IllegalArgumentException e) {
            throw new AcessoNegadoException("Permissão inválida informada.");
        }

        if (!idEditor.equals(requestUpdate.getResponsavel().getIdUsuario()) || !permissaoEditor.equals(PermissaoEnum.DIRETOR)) throw new AcessoNegadoException("Usuário não tem permissão para efetuar a ação");

        Tarefa tarefa = TarefaMapper.toEntity(requestUpdate);
        tarefa.setIdTarefa(idTarefa);

       Tarefa tarefaSalva = tarefaRepository.save(tarefa);
       return passarParaResponse(tarefaSalva);
    }

    public TarefaResponseDto atualizarImpedimento(Integer idTarefa, AtualizarStatusRequestDto request) {
        Optional<Tarefa> optTarefa = tarefaRepository.findById(idTarefa);
        Tarefa tarefa = optTarefa.get();
        Integer fkResponsavel = tarefa.getResponsavel().getIdUsuario();

        if (!request.getIdEditor().equals(fkResponsavel)) throw new AcessoNegadoException("Usuário não é responsável pela tarefa");

        tarefaRepository.toggleImpedimento(idTarefa);

        if (tarefa.getComImpedimento()){
            emailNotifier.update(tarefa);
        }

        return passarParaResponse(tarefa);
    }

    public TarefaResponseDto passarParaResponse(Tarefa tarefa) {
        return TarefaMapper.toDto(tarefa);
    }
}
