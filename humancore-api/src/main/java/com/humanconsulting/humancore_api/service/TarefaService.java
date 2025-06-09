package com.humanconsulting.humancore_api.service;

import com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa.AtualizarStatusRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.TarefaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.controller.dto.response.checkpoint.CheckpointResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.exception.AcessoNegadoException;
import com.humanconsulting.humancore_api.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.mapper.CheckpointMapper;
import com.humanconsulting.humancore_api.mapper.TarefaMapper;
import com.humanconsulting.humancore_api.model.*;
import com.humanconsulting.humancore_api.observer.EmailNotifier;
import com.humanconsulting.humancore_api.observer.SalaNotifier;
import com.humanconsulting.humancore_api.repository.CheckpointRepository;
import com.humanconsulting.humancore_api.repository.SprintRepository;
import com.humanconsulting.humancore_api.repository.TarefaRepository;
import com.humanconsulting.humancore_api.repository.UsuarioRepository;
import com.humanconsulting.humancore_api.security.PermissaoValidator;
import com.humanconsulting.humancore_api.utils.ProgressoCalculator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TarefaService {

    @Autowired private CheckpointService checkpointService;

    @Autowired private TarefaRepository tarefaRepository;

    @Autowired private SprintRepository sprintRepository;

    @Autowired private UsuarioRepository usuarioRepository;

    @Autowired private CheckpointRepository checkpointRepository;

    @Autowired private EmailNotifier emailNotifier;

    @Autowired private UsuarioService usuarioService;

    @Autowired private ProjetoService projetoService;

    @Autowired private SprintService sprintService;

    @Autowired private SalaNotifier salaNotifier;


    @Transactional
    public TarefaResponseDto cadastrar(TarefaRequestDto tarefaRequestDto) {
        PermissaoValidator.validarPermissao(tarefaRequestDto.getPermissaoEditor(), "ADICIONAR_TAREFA");

        Sprint sprint = sprintRepository.findById(tarefaRequestDto.getFkSprint()).get();
        if (tarefaRequestDto.getDtInicio().isAfter(tarefaRequestDto.getDtFim()) ||
                tarefaRequestDto.getDtInicio().isEqual(tarefaRequestDto.getDtFim()) ||
                tarefaRequestDto.getDtInicio().isBefore(sprint.getDtInicio()) ||
                tarefaRequestDto.getDtFim().isAfter(sprint.getDtFim()))
            throw new EntidadeConflitanteException("Datas de início e fim conflitantes.");

        Usuario usuario = usuarioRepository.findById(tarefaRequestDto.getFkResponsavel()).get();
        Tarefa tarefa = tarefaRepository.save(TarefaMapper.toEntity(tarefaRequestDto, sprint, usuario));

        if (tarefa.getResponsavel() != null) {
            salaNotifier.update(tarefa, tarefa.getSprint().getProjeto(), usuario);
        }

        return passarParaResponse(tarefa);
    }

    public Tarefa buscarPorId(Integer id) {
        Optional<Tarefa> optTarefa = tarefaRepository.findById(id);

        if (optTarefa.isEmpty()) throw new EntidadeNaoEncontradaException("Tarefa não encontada");

        return optTarefa.get();
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

    public void deletar(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        PermissaoValidator.validarPermissao(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_TAREFA");

        Optional<Tarefa> optTarefa = tarefaRepository.findById(id);
        if (optTarefa.isEmpty()) throw new EntidadeNaoEncontradaException("Tarefa não encontrada.");

        tarefaRepository.deleteById(id);
    }

    @Transactional
    public TarefaResponseDto atualizar(Integer idTarefa, AtualizarGeralRequestDto requestUpdate) {
        Tarefa tarefa = buscarPorId(idTarefa);

        Optional<Usuario> optUsuarioEditor = usuarioRepository.findById(requestUpdate.getIdEditor());

        if (optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");

        PermissaoValidator.validarPermissao(requestUpdate.getPermissaoEditor(), "MODIFICAR_TAREFA");

        Usuario usuario = usuarioRepository.findById(requestUpdate.getFkResponsavel()).get();

        Tarefa tarefaAtualizada = TarefaMapper.toEntity(requestUpdate, idTarefa, tarefa.getSprint(), usuario);

        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_IdTarefa(tarefa.getIdTarefa());
        List<CheckpointResponseDto> checkpointResponseDtos = new ArrayList<>();
        for (Checkpoint checkpoint : checkpoints) {
            checkpointResponseDtos.add(CheckpointMapper.toDto(checkpoint));
        }

        Double progresso = ProgressoCalculator.calularProgresso(checkpoints);

        if (progresso == 100) tarefaAtualizada.setComImpedimento(false);

        tarefaRepository.save(tarefaAtualizada);

        if (tarefa.getResponsavel() != null) {
            salaNotifier.update(tarefa, tarefa.getSprint().getProjeto(), usuario);
        }

        checkpointService.sincronizarCheckpointsDaTarefa(idTarefa, requestUpdate.getCheckpoints());

        return passarParaResponse(tarefaAtualizada);
    }

    public TarefaResponseDto atualizarImpedimento(Integer idTarefa, AtualizarStatusRequestDto request) {
        Tarefa tarefa = buscarPorId(idTarefa);
        Integer fkResponsavel = tarefa.getResponsavel().getIdUsuario();

        Sprint sprintEntrega = sprintService.buscarPorId(tarefa.getSprint().getIdSprint());
        Projeto projetoEntrega = projetoService.buscarPorId(sprintEntrega.getProjeto().getIdProjeto());
        Usuario tarefaResponsavel = usuarioRepository.findById(tarefa.getResponsavel().getIdUsuario()).get();
        Usuario projetoResponsavel = usuarioRepository.findById(projetoEntrega.getResponsavel().getIdUsuario()).get();
        LoginResponseDto responsavelProjeto = usuarioService.passarParaLoginResponse(projetoResponsavel, null);
        LoginResponseDto responsavelEntrega = usuarioService.passarParaLoginResponse(tarefaResponsavel, null);

        if (!request.getIdEditor().equals(fkResponsavel))
            throw new AcessoNegadoException("Usuário não é responsável pela tarefa");

        tarefaRepository.toggleImpedimento(idTarefa);

        emailNotifier.update(tarefa, sprintEntrega, projetoEntrega, tarefaResponsavel, projetoResponsavel, responsavelProjeto, responsavelEntrega);

        return passarParaResponse(tarefa);
    }

    public TarefaResponseDto passarParaResponse(Tarefa tarefa) {
        List<Checkpoint> checkpoints = checkpointRepository.findAllByTarefa_IdTarefa(tarefa.getIdTarefa());
        List<CheckpointResponseDto> checkpointResponseDtos = new ArrayList<>();
        for (Checkpoint checkpoint : checkpoints) {
            checkpointResponseDtos.add(CheckpointMapper.toDto(checkpoint));
        }

        Double progresso = ProgressoCalculator.calularProgresso(checkpoints);

        return TarefaMapper.toDto(tarefa, checkpointResponseDtos, progresso);
    }
}
