package com.humanconsulting.humancore_api.novo.application.usecases.tarefa;

import com.humanconsulting.humancore_api.novo.application.usecases.projeto.BuscarProjetoPorIdUseCase;
import com.humanconsulting.humancore_api.novo.application.usecases.sprint.BuscarSprintPorIdUseCase;
import com.humanconsulting.humancore_api.novo.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.novo.application.usecases.usuario.mappers.UsuarioLoginResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.domain.entities.Sprint;
import com.humanconsulting.humancore_api.novo.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.novo.domain.entities.Usuario;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.novo.domain.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.novo.domain.notifiers.EmailNotifier;
import com.humanconsulting.humancore_api.novo.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.atualizar.tarefa.AtualizarStatusRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.usuario.LoginResponseDto;

import java.util.Optional;

public class AtualizarImpedimentoTarefaUseCase {
    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmailNotifier emailNotifier;
    private final BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase;
    private final BuscarSprintPorIdUseCase buscarSprintPorIdUseCase;
    private final UsuarioLoginResponseMapper usuarioMapper;
    private final TarefaResponseMapper tarefaResponseMapper;

    public AtualizarImpedimentoTarefaUseCase(
            TarefaRepository tarefaRepository,
            UsuarioRepository usuarioRepository,
            EmailNotifier emailNotifier,
            BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase,
            BuscarSprintPorIdUseCase buscarSprintPorIdUseCase,
            UsuarioLoginResponseMapper usuarioMapper,
            TarefaResponseMapper tarefaResponseMapper
    ) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
        this.emailNotifier = emailNotifier;
        this.buscarProjetoPorIdUseCase = buscarProjetoPorIdUseCase;
        this.buscarSprintPorIdUseCase = buscarSprintPorIdUseCase;
        this.usuarioMapper = usuarioMapper;
        this.tarefaResponseMapper = tarefaResponseMapper;
    }

    public TarefaResponseDto execute(Integer idTarefa, AtualizarStatusRequestDto request) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(idTarefa);
        if (tarefa.isEmpty()) {
            throw new EntidadeNaoEncontradaException("TarefaEntity não encontrada");
        }
        Integer fkResponsavel = tarefa.get().getResponsavel().getIdUsuario();
        Sprint sprintEntrega = buscarSprintPorIdUseCase.execute(tarefa.get().getSprint().getIdSprint());
        Projeto projetoEntrega = buscarProjetoPorIdUseCase.execute(sprintEntrega.getProjeto().getIdProjeto());
        Optional<Usuario> tarefaResponsavel = usuarioRepository.findById(tarefa.get().getResponsavel().getIdUsuario());
        if (tarefaResponsavel.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Usuário responsável pela tarefa não encontrado");
        }
        Optional<Usuario> projetoResponsavel = usuarioRepository.findById(projetoEntrega.getResponsavel().getIdUsuario());
        if (projetoResponsavel.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Usuário responsável pelo projeto não encontrado");
        }
        LoginResponseDto responsavelProjeto = usuarioMapper.toLoginResponse(projetoResponsavel.orElse(null), null);
        LoginResponseDto responsavelEntrega = usuarioMapper.toLoginResponse(tarefaResponsavel.orElse(null), null);
        if (!request.getIdEditor().equals(fkResponsavel))
            throw new EntidadeSemPermissaoException("Usuário não é responsável pela tarefa");
        tarefaRepository.toggleImpedimento(idTarefa);
        emailNotifier.update(tarefa.orElse(null), sprintEntrega, projetoEntrega, tarefaResponsavel.orElse(null), projetoResponsavel.orElse(null), responsavelProjeto, responsavelEntrega);
        return tarefaResponseMapper.toResponse(tarefa.orElse(null));
    }
}
