package com.humanconsulting.humancore_api.novo.application.usecases.tarefa;

import com.humanconsulting.humancore_api.novo.application.usecases.checkpoint.SincronizarCheckpointsDaTarefaUseCase;
import com.humanconsulting.humancore_api.novo.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.novo.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.atualizar.tarefa.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.TarefaMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AtualizarTarefaUseCase {
    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;
    private final CheckpointRepository checkpointRepository;
    private final SalaNotifier salaNotifier;
    private final TarefaResponseMapper tarefaResponseMapper;
    private final SincronizarCheckpointsDaTarefaUseCase sincronizarCheckpointsDaTarefa;

    public AtualizarTarefaUseCase(
            TarefaRepository tarefaRepository,
            UsuarioRepository usuarioRepository,
            CheckpointRepository checkpointRepository,
            SalaNotifier salaNotifier,
            TarefaResponseMapper tarefaResponseMapper,
            SincronizarCheckpointsDaTarefaUseCase sincronizarCheckpointsDaTarefa
    ) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
        this.checkpointRepository = checkpointRepository;
        this.salaNotifier = salaNotifier;
        this.tarefaResponseMapper = tarefaResponseMapper;
        this.sincronizarCheckpointsDaTarefa = sincronizarCheckpointsDaTarefa;
    }

    public TarefaResponseDto execute(Integer idTarefa, AtualizarGeralRequestDto requestUpdate) {
        Tarefa tarefa = tarefaRepository.findById(idTarefa);
        if (tarefa == null) {
            throw new EntidadeNaoEncontradaException("TarefaEntity não encontrada");
        }
        Optional<Usuario> optUsuarioEditor = usuarioRepository.findById(requestUpdate.getIdEditor());
        if (optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
        PermissaoValidator.validarPermissao(requestUpdate.getPermissaoEditor(), "MODIFICAR_TAREFA");
        Usuario usuario = usuarioRepository.findById(requestUpdate.getFkResponsavel()).get();
        Tarefa tarefaAtualizada = TarefaMapper.toEntity(requestUpdate, idTarefa, tarefa.getSprint(), usuario);
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_IdTarefa(tarefa.getIdTarefa());
        Double progresso = ProgressoCalculator.calularProgresso(checkpoints);
        if (progresso == 100) tarefaAtualizada.setComImpedimento(false);
        tarefaRepository.save(tarefaAtualizada);
        if (tarefa.getResponsavel() != null) {
            salaNotifier.adicionarUsuarioEmSalaProjeto(tarefa, tarefa.getSprint().getProjeto(), usuario);
        }
        sincronizarCheckpointsDaTarefa.execute(idTarefa, requestUpdate.getCheckpoints());
        return tarefaResponseMapper.toResponse(tarefaAtualizada);
    }
}
