package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;
import com.humanconsulting.humancore_api.web.mappers.ProjetoMapper;

public class AtualizarProjetoUseCase {
    private final ProjetoRepository projetoRepository;
    private final UsuarioRepository usuarioRepository;
    private final BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase;
    private final ProjetoResponseMapper projetoResponseMapper;

    public AtualizarProjetoUseCase(ProjetoRepository projetoRepository, UsuarioRepository usuarioRepository, BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase, ProjetoResponseMapper projetoResponseMapper) {
        this.projetoRepository = projetoRepository;
        this.usuarioRepository = usuarioRepository;
        this.buscarProjetoPorIdUseCase = buscarProjetoPorIdUseCase;
        this.projetoResponseMapper = projetoResponseMapper;
    }

    public ProjetoResponseDto execute(Integer idProjeto, ProjetoAtualizarRequestDto projetoAtualizarRequestDto) {
        Projeto projeto = buscarProjetoPorIdUseCase.execute(idProjeto);
        String urlImagemOriginal = projetoRepository.findUrlImagemById(idProjeto);
        if (projetoAtualizarRequestDto.getUrlImagem().isEmpty()) projetoAtualizarRequestDto.setUrlImagem(urlImagemOriginal);
        Usuario usuarioEditor = usuarioRepository.findById(projetoAtualizarRequestDto.getIdEditor()).orElseThrow(() -> new EntidadeNaoEncontradaException("Usuário não encontrado."));
        ValidarPermissao.execute(projetoAtualizarRequestDto.getPermissaoEditor(), "MODIFICAR_PROJETO");
        Usuario usuarioResponsavel = usuarioRepository.findById(projetoAtualizarRequestDto.getFkResponsavel()).get();
        Projeto projetoAtualizado = projetoRepository.save(ProjetoMapper.toEntity(projetoAtualizarRequestDto, idProjeto, usuarioResponsavel, projeto.getEmpresa()));
        return projetoResponseMapper.toResponse(projetoAtualizado, projetoAtualizado.getResponsavel().getIdUsuario(), projetoAtualizado.getIdProjeto());
    }
}

