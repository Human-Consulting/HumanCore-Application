package com.humanconsulting.humancore_api.novo.application.usecases.tarefa;

import com.humanconsulting.humancore_api.novo.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Sprint;
import com.humanconsulting.humancore_api.novo.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.repositories.SprintRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.request.TarefaRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.tarefa.TarefaResponseDto;

public class CadastrarTarefaUseCase {
    private final TarefaRepository tarefaRepository;
    private final SprintRepository sprintRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaNotifier salaNotifier;
    private final TarefaResponseMapper tarefaResponseMapper;

    public CadastrarTarefaUseCase(
            TarefaRepository tarefaRepository,
            SprintRepository sprintRepository,
            UsuarioRepository usuarioRepository,
            SalaNotifier salaNotifier,
            TarefaResponseMapper tarefaResponseMapper
    ) {
        this.tarefaRepository = tarefaRepository;
        this.sprintRepository = sprintRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaNotifier = salaNotifier;
        this.tarefaResponseMapper = tarefaResponseMapper;
    }

    public TarefaResponseDto execute(TarefaRequestDto tarefaRequestDto) {
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
            salaNotifier.adicionarUsuarioEmSalaProjeto(tarefa, tarefa.getSprint().getProjeto(), usuario);
        }
        return tarefaResponseMapper.toResponse(tarefa);
    }
}

