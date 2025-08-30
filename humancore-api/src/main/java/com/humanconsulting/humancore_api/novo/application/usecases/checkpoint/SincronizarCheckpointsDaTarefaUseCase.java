package com.humanconsulting.humancore_api.novo.application.usecases.checkpoint;

import com.humanconsulting.humancore_api.novo.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.novo.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.novo.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.request.CheckpointRequestDto;
import com.humanconsulting.humancore_api.novo.web.mappers.CheckpointMapper;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SincronizarCheckpointsDaTarefaUseCase {
    private final CheckpointRepository checkpointRepository;
    private final TarefaRepository tarefaRepository;

    public SincronizarCheckpointsDaTarefaUseCase(CheckpointRepository checkpointRepository, TarefaRepository tarefaRepository) {
        this.checkpointRepository = checkpointRepository;
        this.tarefaRepository = tarefaRepository;
    }

    public void execute(Integer idTarefa, List<CheckpointRequestDto> recebidos) {
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
            checkpointRepository.save(c);
        }

        // 6. Remove os que estão no banco mas não vieram
        for (Checkpoint c : atuais) {
            if (c.getIdCheckpoint() != null && !idsRecebidos.contains(c.getIdCheckpoint())) {
                checkpointRepository.delete(c);
            }
        }
    }
}

