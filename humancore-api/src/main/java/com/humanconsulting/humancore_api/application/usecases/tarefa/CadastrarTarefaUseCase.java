package com.humanconsulting.humancore_api.application.usecases.tarefa;

import com.humanconsulting.humancore_api.application.usecases.checkpoint.SincronizarCheckpointsDaTarefaUseCase;
import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.infrastructure.configs.calendar.GoogleCalendarService;
import com.humanconsulting.humancore_api.web.dtos.request.TarefaRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.TarefaMapper;

import java.io.IOException;

public class CadastrarTarefaUseCase {
    private final TarefaRepository tarefaRepository;
    private final SprintRepository sprintRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaNotifier salaNotifier;
    private final TarefaResponseMapper tarefaResponseMapper;
    private final SincronizarCheckpointsDaTarefaUseCase sincronizarCheckpointsDaTarefaUseCase;
    private final GoogleCalendarService googleCalendarService;

    public CadastrarTarefaUseCase(
            TarefaRepository tarefaRepository,
            SprintRepository sprintRepository,
            UsuarioRepository usuarioRepository,
            SalaNotifier salaNotifier,
            TarefaResponseMapper tarefaResponseMapper,
            SincronizarCheckpointsDaTarefaUseCase sincronizarCheckpointsDaTarefaUseCase,
            GoogleCalendarService googleCalendarService
    ) {
        this.tarefaRepository = tarefaRepository;
        this.sprintRepository = sprintRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaNotifier = salaNotifier;
        this.tarefaResponseMapper = tarefaResponseMapper;
        this.sincronizarCheckpointsDaTarefaUseCase = sincronizarCheckpointsDaTarefaUseCase;
        this.googleCalendarService = googleCalendarService;
    }

    public TarefaResponseDto execute(TarefaRequestDto tarefaRequestDto) throws IOException {
        ValidarPermissao.execute(tarefaRequestDto.getPermissaoEditor(), "ADICIONAR_TAREFA");
        Sprint sprint = sprintRepository.findById(tarefaRequestDto.getFkSprint()).get();
        if (tarefaRequestDto.getDtInicio().isAfter(tarefaRequestDto.getDtFim()) ||
                tarefaRequestDto.getDtInicio().isEqual(tarefaRequestDto.getDtFim()) ||
                tarefaRequestDto.getDtInicio().isBefore(sprint.getDtInicio()) ||
                tarefaRequestDto.getDtFim().isAfter(sprint.getDtFim()))
            throw new EntidadeConflitanteException("Datas de início e fim conflitantes.");
        Usuario usuario = usuarioRepository.findById(tarefaRequestDto.getFkResponsavel()).get();
        Tarefa tarefa = tarefaRepository.save(TarefaMapper.toEntity(tarefaRequestDto, sprint, usuario));

        if (!tarefaRequestDto.getCheckpoints().isEmpty()) sincronizarCheckpointsDaTarefaUseCase.execute(tarefa.getIdTarefa(), tarefaRequestDto.getCheckpoints());

        if (tarefa.getResponsavel() != null) salaNotifier.adicionarUsuarioEmSalaProjeto(tarefa, tarefa.getSprint().getProjeto(), usuario);

        String tituloEvento = "Tarefa: " + tarefa.getTitulo();
        String descricaoEvento = "Descrição: " + tarefa.getDescricao() + "\n" +
                "Início: " + tarefa.getDtInicio() + "\n" +
                "Fim: " + tarefa.getDtFim() + "\n" +
                "Responsável: " + (tarefa.getResponsavel() != null ? tarefa.getResponsavel().getNome() : "Não atribuído");
        googleCalendarService.criarEvento(tituloEvento, descricaoEvento);

        return tarefaResponseMapper.toResponse(tarefa);
    }
}

