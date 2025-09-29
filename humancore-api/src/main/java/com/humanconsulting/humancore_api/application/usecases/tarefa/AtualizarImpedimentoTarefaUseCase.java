package com.humanconsulting.humancore_api.application.usecases.tarefa;

import com.humanconsulting.humancore_api.application.usecases.projeto.BuscarProjetoPorIdUseCase;
import com.humanconsulting.humancore_api.application.usecases.sprint.BuscarSprintPorIdUseCase;
import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioLoginResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.domain.notifiers.EmailNotifier;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.atualizar.tarefa.AtualizarStatusRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.sprint.SprintResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.usuario.LoginResponseDto;
import com.humanconsulting.humancore_api.web.mappers.SprintMapper;

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
        Sprint sprintEntrega = SprintMapper.toEntity(buscarSprintPorIdUseCase.execute(tarefa.get().getSprint().getIdSprint()));
        Projeto projetoEntrega = buscarProjetoPorIdUseCase.execute(sprintEntrega.getProjeto().getIdProjeto());
        Optional<Usuario> tarefaResponsavel = usuarioRepository.findById(tarefa.get().getResponsavel().getIdUsuario());
        if (tarefaResponsavel.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Usuário responsável pela tarefa não encontrado");
        }
        Optional<Usuario> projetoResponsavel = usuarioRepository.findById(projetoEntrega.getResponsavel().getIdUsuario());
        if (projetoResponsavel.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Usuário responsável pelo projeto não encontrado");
        }
        LoginResponseDto responsavelProjeto = usuarioMapper.toLoginResponse(projetoResponsavel.get(), null);
        LoginResponseDto responsavelEntrega = usuarioMapper.toLoginResponse(tarefaResponsavel.get(), null);
        if (!request.getIdEditor().equals(fkResponsavel))
            throw new EntidadeSemPermissaoException("Usuário não é responsável pela tarefa");
        tarefaRepository.toggleImpedimento(idTarefa);
        emailNotifier.update(tarefa.get(), sprintEntrega, projetoEntrega, tarefaResponsavel.get(), projetoResponsavel.get(), responsavelProjeto, responsavelEntrega);
        return tarefaResponseMapper.toResponse(tarefa.get());
    }
}
