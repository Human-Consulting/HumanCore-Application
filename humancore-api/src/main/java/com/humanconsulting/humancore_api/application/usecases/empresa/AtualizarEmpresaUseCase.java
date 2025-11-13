package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.EmpresaMapper;

import java.util.Optional;

public class AtualizarEmpresaUseCase {
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final EmpresaResponseMapper empresaResponseMapper;

    public AtualizarEmpresaUseCase(EmpresaRepository empresaRepository,
                                   UsuarioRepository usuarioRepository,
                                   EmpresaResponseMapper empresaResponseMapper) {
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
        this.empresaResponseMapper = empresaResponseMapper;
    }

    public EmpresaResponseDto execute(Integer idEmpresa, EmpresaAtualizarRequestDto request) {
        String urlImagemOriginal = empresaRepository.findUrlImagemById(idEmpresa);
        if (request.getUrlImagem().isEmpty()) request.setUrlImagem(urlImagemOriginal);
        Optional<?> optUsuarioEditor = usuarioRepository.findById(request.getIdEditor());
        if (optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
        ValidarPermissao.execute(request.getPermissaoEditor(), "MODIFICAR_EMPRESA");
        Empresa empresaAtualizada = empresaRepository.save(EmpresaMapper.toEntity(request, idEmpresa));
        return empresaResponseMapper.toResponse(empresaAtualizada);
    }
}
