package com.humanconsulting.humancore_api.application.usecases.tarefa;

import com.humanconsulting.humancore_api.application.usecases.checkpoint.SincronizarCheckpointsDaTarefaUseCase;
import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Checkpoint;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.CheckpointRepository;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.domain.utils.ProgressoCalculator;
import com.humanconsulting.humancore_api.web.dtos.atualizar.tarefa.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.TarefaMapper;

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
        Optional<Tarefa>    tarefa = tarefaRepository.findById(idTarefa);
        if (tarefa.isEmpty()) {
            throw new EntidadeNaoEncontradaException("TarefaEntity não encontrada");
        }
        Optional<Usuario> optUsuarioEditor = usuarioRepository.findById(requestUpdate.getIdEditor());
        if (optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
        ValidarPermissao.execute(requestUpdate.getPermissaoEditor(), "MODIFICAR_TAREFA");
        Usuario usuario = requestUpdate.getFkResponsavel() != null ? usuarioRepository.findById(requestUpdate.getFkResponsavel()).get() : null;
        Tarefa tarefaAtualizada = TarefaMapper.toEntity(requestUpdate, idTarefa, tarefa.get().getSprint(), usuario);
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_IdTarefa(tarefa.get().getIdTarefa());
        Double progresso = ProgressoCalculator.execute(checkpoints);
        if (progresso == 100) tarefaAtualizada.setComImpedimento(false);
        tarefaRepository.save(tarefaAtualizada);
        if (tarefa.get().getResponsavel() != null) {
            salaNotifier.adicionarUsuarioEmSalaProjeto(tarefa.orElse(null), tarefa.get().getSprint().getProjeto(), usuario);
        }
        sincronizarCheckpointsDaTarefa.execute(idTarefa, requestUpdate.getCheckpoints());
        return tarefaResponseMapper.toResponse(tarefaAtualizada);
    }
}
