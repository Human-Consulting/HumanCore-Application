package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeConflitanteException;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.web.mappers.ProjetoMapper;

public class CadastrarProjetoUseCase {
    private final ProjetoRepository projetoRepository;
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaNotifier salaNotifier;
    private final ProjetoResponseMapper projetoResponseMapper;

    public CadastrarProjetoUseCase(ProjetoRepository projetoRepository, EmpresaRepository empresaRepository, UsuarioRepository usuarioRepository, SalaNotifier salaNotifier, ProjetoResponseMapper projetoResponseMapper) {
        this.projetoRepository = projetoRepository;
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaNotifier = salaNotifier;
        this.projetoResponseMapper = projetoResponseMapper;
    }

    public ProjetoResponseDto execute(ProjetoRequestDto projetoRequestDto) {
        ValidarPermissao.execute(projetoRequestDto.getPermissaoEditor(), "ADICIONAR_PROJETO");
        if (projetoRepository.existsByEmpresa_IdEmpresaAndDescricao(projetoRequestDto.getFkEmpresa(), projetoRequestDto.getDescricao())) {
            throw new EntidadeConflitanteException("ProjetoEntity já cadastrado");
        }
        Projeto projeto = projetoRepository.save(
           ProjetoMapper.toEntity(
                projetoRequestDto,
                empresaRepository.findById(projetoRequestDto.getFkEmpresa()).get(),
                usuarioRepository.findById(projetoRequestDto.getFkResponsavel()).get()
            )
        );
        Usuario editor = usuarioRepository.findById(projetoRequestDto.getIdEditor()).get();
        salaNotifier.onProjetoCriado(projeto, editor);
        return projetoResponseMapper.toResponse(projeto, projeto.getResponsavel().getIdUsuario(), projeto.getIdProjeto());
    }
}

