package com.humanconsulting.humancore_api.application.usecases.tarefa;

import com.humanconsulting.humancore_api.application.usecases.projeto.BuscarProjetoPorIdUseCase;
import com.humanconsulting.humancore_api.application.usecases.sprint.BuscarSprintPorIdUseCase;
import com.humanconsulting.humancore_api.application.usecases.tarefa.mappers.TarefaResponseMapper;
import com.humanconsulting.humancore_api.application.usecases.usuario.mappers.UsuarioResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemPermissaoException;
import com.humanconsulting.humancore_api.infrastructure.configs.RabbitTemplateConfiguration;
import com.humanconsulting.humancore_api.infrastructure.mappers.EmailUpdateMapper;
import com.humanconsulting.humancore_api.domain.repositories.TarefaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.web.dtos.atualizar.tarefa.AtualizarStatusRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.email.EmailUpdateResponseDto;
import com.humanconsulting.humancore_api.web.dtos.response.tarefa.TarefaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.SprintMapper;

import java.util.Optional;

public class AtualizarImpedimentoTarefaUseCase {
    private final TarefaRepository tarefaRepository;
    private final UsuarioRepository usuarioRepository;
    private final RabbitTemplateConfiguration rabbitMQ;
    private final BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase;
    private final BuscarSprintPorIdUseCase buscarSprintPorIdUseCase;
    private final UsuarioResponseMapper usuarioMapper;
    private final TarefaResponseMapper tarefaResponseMapper;
    private final EmailUpdateMapper emailUpdateMapper;

    public AtualizarImpedimentoTarefaUseCase(
            TarefaRepository tarefaRepository,
            UsuarioRepository usuarioRepository,
            RabbitTemplateConfiguration rabbitMQ,
            BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase,
            BuscarSprintPorIdUseCase buscarSprintPorIdUseCase,
            UsuarioResponseMapper usuarioMapper,
            TarefaResponseMapper tarefaResponseMapper,
            EmailUpdateMapper emailUpdateMapper
    ) {
        this.tarefaRepository = tarefaRepository;
        this.usuarioRepository = usuarioRepository;
        this.rabbitMQ = rabbitMQ;
        this.buscarProjetoPorIdUseCase = buscarProjetoPorIdUseCase;
        this.buscarSprintPorIdUseCase = buscarSprintPorIdUseCase;
        this.usuarioMapper = usuarioMapper;
        this.tarefaResponseMapper = tarefaResponseMapper;
        this.emailUpdateMapper = emailUpdateMapper;
    }

    public TarefaResponseDto execute(Integer idTarefa, AtualizarStatusRequestDto request) {
        Optional<Tarefa> tarefa = tarefaRepository.findById(idTarefa);
        if (tarefa.isEmpty()) {
            throw new EntidadeNaoEncontradaException("TarefaEntity não encontrada");
        }
        Integer fkResponsavel = tarefa.get().getResponsavel().getIdUsuario();
        Sprint sprintTarefa = SprintMapper.toEntity(buscarSprintPorIdUseCase.execute(tarefa.get().getSprint().getIdSprint()));
        Projeto projetoTarefa = buscarProjetoPorIdUseCase.execute(sprintTarefa.getProjeto().getIdProjeto());
        Optional<Usuario> tarefaResponsavel = usuarioRepository.findById(tarefa.get().getResponsavel().getIdUsuario());
        if (tarefaResponsavel.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Usuário responsável pela tarefa não encontrado");
        }
        Optional<Usuario> projetoResponsavel = usuarioRepository.findById(projetoTarefa.getResponsavel().getIdUsuario());
        if (projetoResponsavel.isEmpty()) {
            throw new EntidadeNaoEncontradaException("Usuário responsável pelo projeto não encontrado");
        }

        if (!request.getIdEditor().equals(fkResponsavel))
            throw new EntidadeSemPermissaoException("Usuário não é responsável pela tarefa");
        tarefaRepository.toggleImpedimento(idTarefa);

        EmailUpdateResponseDto emailUpdateResponseDto = emailUpdateMapper.toEmailUpdateResponseDto(
                tarefa.get(),
                sprintTarefa,
                projetoTarefa,
                tarefaResponsavel.get(),
                projetoResponsavel.get()
        );
        rabbitMQ.rabbitTemplate().convertAndSend(
                "update",
                emailUpdateResponseDto
        );

        return tarefaResponseMapper.toResponse(tarefa.get());
    }
}
