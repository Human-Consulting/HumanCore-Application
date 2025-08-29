package com.humanconsulting.humancore_api.novo.application.usecases.tarefa;

import com.humanconsulting.humancore_api.novo.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.domain.entities.Sprint;
import com.humanconsulting.humancore_api.novo.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.atualizar.tarefa.AtualizarStatusRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.usuario.LoginResponseDto;

public class AtualizarImpedimentoTarefaUseCase {
    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmailNotifier emailNotifier;
    private final ProjetoService projetoService;
    private final SprintService sprintService;
    private final UsuarioService usuarioService;
    private final TarefaResponseMapper tarefaResponseMapper;

    public AtualizarImpedimentoTarefaUseCase(
            TarefaRepository tarefaRepository,
            UsuarioRepository usuarioRepository,
            EmailNotifier emailNotifier,
            ProjetoService projetoService,
            SprintService sprintService,
            UsuarioService usuarioService,
            TarefaResponseMapper tarefaResponseMapper
    ) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
        this.emailNotifier = emailNotifier;
        this.projetoService = projetoService;
        this.sprintService = sprintService;
        this.usuarioService = usuarioService;
        this.tarefaResponseMapper = tarefaResponseMapper;
    }

    public TarefaResponseDto execute(Integer idTarefa, AtualizarStatusRequestDto request) {
        Tarefa tarefa = tarefaRepository.findById(idTarefa)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("TarefaEntity não encontrada"));
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
        return tarefaResponseMapper.toResponse(tarefa);
    }
}

