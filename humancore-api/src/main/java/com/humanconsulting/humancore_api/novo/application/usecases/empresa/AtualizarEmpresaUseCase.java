package com.humanconsulting.humancore_api.novo.application.usecases.empresa;

import com.humanconsulting.humancore_api.novo.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.novo.web.mappers.EmpresaMapper;

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
        PermissaoValidator.validarPermissao(request.getPermissaoEditor(), "MODIFICAR_EMPRESA");
        Empresa empresaAtualizada = empresaRepository.save(EmpresaMapper.toEntity(request, idEmpresa));
        return empresaResponseMapper.toResponse(empresaAtualizada);
    }
}

