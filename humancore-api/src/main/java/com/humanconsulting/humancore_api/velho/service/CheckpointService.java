package com.humanconsulting.humancore_api.velho.service;

import com.humanconsulting.humancore_api.velho.controller.dto.request.CheckpointRequestDto;
import com.humanconsulting.humancore_api.velho.mapper.CheckpointMapper;
import com.humanconsulting.humancore_api.velho.model.Checkpoint;
import com.humanconsulting.humancore_api.velho.model.Tarefa;
import com.humanconsulting.humancore_api.velho.repository.CheckpointRepository;
import com.humanconsulting.humancore_api.velho.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CheckpointService {

    @Autowired
    CheckpointRepository checkpointRepository;

    @Autowired
    TarefaRepository tarefaRepository;

    public void sincronizarCheckpointsDaTarefa(Integer idTarefa, List<CheckpointRequestDto> recebidos) {
        // 1. Busca do banco
        List<Checkpoint> atuais = checkpointRepository.findAllByTarefa_IdTarefa(idTarefa);

        // 2. Converte lista recebida em entidades associadas à tarefa
        Tarefa tarefa = tarefaRepository.findById(idTarefa).get();
        List<Checkpoint> recebidosConvertidos = new ArrayList<>();
        for (CheckpointRequestDto recebido : recebidos) {
            recebidosConvertidos.add(CheckpointMapper.toEntity(recebido, tarefa));
        }

        // 3. Cria mapa dos atuais para acesso rápido por ID
        Map<Integer, Checkpoint> mapaAtuais = atuais.stream()
                .filter(c -> c.getIdCheckpoint() != null)
                .collect(Collectors.toMap(Checkpoint::getIdCheckpoint, Function.identity()));

        // 4. IDs recebidos
        Set<Integer> idsRecebidos = recebidosConvertidos.stream()
                .map(Checkpoint::getIdCheckpoint)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        // 5. Atualiza ou salva os recebidos
        for (Checkpoint c : recebidosConvertidos) {
            if (c.getIdCheckpoint() == null) {
                checkpointRepository.save(c); // Novo
            } else {
                checkpointRepository.save(c); // Update
            }
        }

        // 6. Remove os que estão no banco mas não vieram
        for (Checkpoint c : atuais) {
            if (c.getIdCheckpoint() != null && !idsRecebidos.contains(c.getIdCheckpoint())) {
                checkpointRepository.delete(c);
            }
        }
    }

}
