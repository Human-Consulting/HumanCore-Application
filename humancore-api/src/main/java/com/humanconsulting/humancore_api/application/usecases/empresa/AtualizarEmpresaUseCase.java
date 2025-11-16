package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.application.usecases.sala.AtualizarSalaUseCase;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.repositories.SalaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.web.dtos.request.SalaRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.EmpresaMapper;

import java.util.List;
import java.util.Optional;

public class AtualizarEmpresaUseCase {
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaRepository salaRepository;
    private final EmpresaResponseMapper empresaResponseMapper;
    private final AtualizarSalaUseCase atualizarSalaUseCase;

    public AtualizarEmpresaUseCase(EmpresaRepository empresaRepository,
                                   UsuarioRepository usuarioRepository,
                                   SalaRepository salaRepository,
                                   EmpresaResponseMapper empresaResponseMapper,
                                   AtualizarSalaUseCase atualizarSalaUseCase) {
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaRepository = salaRepository;
        this.empresaResponseMapper = empresaResponseMapper;
        this.atualizarSalaUseCase = atualizarSalaUseCase;
    }

    public EmpresaResponseDto execute(Integer idEmpresa, EmpresaAtualizarRequestDto request) {
        String urlImagemOriginal = empresaRepository.findUrlImagemById(idEmpresa);
        if (request.getUrlImagem().isEmpty()) request.setUrlImagem(urlImagemOriginal);
        Optional<?> optUsuarioEditor = usuarioRepository.findById(request.getIdEditor());
        if (optUsuarioEditor.isEmpty()) throw new EntidadeNaoEncontradaException("Usuário não encontrado.");
        ValidarPermissao.execute(request.getPermissaoEditor(), "MODIFICAR_EMPRESA");
        Empresa empresaAtualizada = empresaRepository.save(EmpresaMapper.toEntity(request, idEmpresa));
        Sala salaVinculada = salaRepository.findByEmpresa(empresaAtualizada);
        if (salaVinculada != null) {
            List<Integer> usuarios = salaVinculada.getUsuarios()
                    .stream()
                    .map(usuario -> usuario.getIdUsuario())
                    .toList();
            atualizarSalaUseCase.execute(salaVinculada.getIdSala(), new SalaRequestDto(empresaAtualizada.getNome(), empresaAtualizada.getUrlImagem(), empresaAtualizada.getIdEmpresa(), null, usuarios, request.getIdEditor()));
        }
        return empresaResponseMapper.toResponse(empresaAtualizada);
    }
}
