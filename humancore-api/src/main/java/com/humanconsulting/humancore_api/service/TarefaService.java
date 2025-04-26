package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa.AtualizarStatusRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.TarefaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.TarefaResponseDto;
import com.humanconsulting.humancore_api.exception.AcessoNegadoException;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.TarefaMapper;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.model.Tarefa;
import com.humanconsulting.humancore_api.model.Usuario;
import com.humanconsulting.humancore_api.repository.TarefaRepository;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TarefaService {

    @Autowired private TarefaRepository tarefaRepository;

    @Autowired private UsuarioRepository usuarioRepository;

    public TarefaResponseDto cadastrar(TarefaRequestDto tarefaRequestDto) {
        if (tarefaRequestDto.getDtInicio().isAfter(tarefaRequestDto.getDtFim()) || tarefaRequestDto.getDtInicio().isEqual(tarefaRequestDto.getDtFim())) throw new EntidadeConflitanteException("Datas de início e fim conflitantes.");
        Tarefa tarefa = tarefaRepository.insert(TarefaMapper.toEntity(tarefaRequestDto));
        return passarParaResponse(tarefa, tarefa.getFkResponsavel());
    }

    public TarefaResponseDto buscarPorId(Integer id) {
        Tarefa tarefa = tarefaRepository.selectWhereId(id);
        return passarParaResponse(tarefa, tarefa.getFkResponsavel());
    }

    public List<TarefaResponseDto> listar() {
        List<Tarefa> all = tarefaRepository.selectAll();
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma tarefa registrada");
        List<TarefaResponseDto> allResponse = new ArrayList<>();
        for (Tarefa tarefa : all) {
            allResponse.add(passarParaResponse(tarefa, tarefa.getFkResponsavel()));
        }
        return allResponse;
    }

    public List<TarefaResponseDto> listarPorSprint(Integer idSprint) {
        List<Tarefa> all = tarefaRepository.selectWhereIdSprint(idSprint);
        if (all.isEmpty()) throw new EntidadeSemRetornoException("Nenhuma tarefa registrada");
        List<TarefaResponseDto> allResponse = new ArrayList<>();
        for (Tarefa tarefa : all) {
            allResponse.add(passarParaResponse(tarefa, tarefa.getFkResponsavel()));
        }
        return allResponse;
    }

    public void deletar(Integer id) {
        tarefaRepository.deleteWhere(id);
    }

    public TarefaResponseDto atualizar(Integer idTarefa, AtualizarGeralRequestDto requestUpdate) {
       Tarefa tarefa = tarefaRepository.update(idTarefa, requestUpdate);
       return passarParaResponse(tarefa, tarefa.getFkResponsavel());
    }

    public TarefaResponseDto atualizarFinalizada(Integer idTarefa, AtualizarStatusRequestDto request) {
        tarefaRepository.existsById(idTarefa);
        Tarefa tarefa = tarefaRepository.updateFinalizar(idTarefa);
        return passarParaResponse(tarefa, request.getIdEditor());
    }

    public TarefaResponseDto atualizarImpedimento(Integer idTarefa, AtualizarStatusRequestDto request) {
        Integer fkResponsavel = tarefaRepository.selectWhereId(idTarefa).getFkResponsavel();
        if (request.getIdEditor() == fkResponsavel) {
            Tarefa tarefa = tarefaRepository.updateImpedimento(idTarefa);
            return passarParaResponse(tarefa, request.getIdEditor());
        }
        throw new AcessoNegadoException("Usuário não é responsável pela tarefa");
    }

    public TarefaResponseDto passarParaResponse(Tarefa tarefa, Integer fkResponsavel) {
        Usuario usuario = usuarioRepository.selectWhereId(fkResponsavel);
        return TarefaMapper.toDto(tarefa, usuario.getNome(), usuario.getArea());
    }
}
