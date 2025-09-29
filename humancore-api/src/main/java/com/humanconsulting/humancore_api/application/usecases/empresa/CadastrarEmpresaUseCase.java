package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.notifiers.SalaNotifier;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.web.mappers.EmpresaMapper;

public class CadastrarEmpresaUseCase {
    private final EmpresaRepository empresaRepository;
    private final UsuarioRepository usuarioRepository;
    private final SalaNotifier salaNotifier;
    private final EmpresaResponseMapper empresaResponseMapper;

    public CadastrarEmpresaUseCase(EmpresaRepository empresaRepository,
                                   UsuarioRepository usuarioRepository,
                                   SalaNotifier salaNotifier,
                                   EmpresaResponseMapper empresaResponseMapper) {
        this.empresaRepository = empresaRepository;
        this.usuarioRepository = usuarioRepository;
        this.salaNotifier = salaNotifier;
        this.empresaResponseMapper = empresaResponseMapper;
    }

    public EmpresaResponseDto execute(EmpresaRequestDto empresaRequestDto) {
        ValidarPermissao.execute(empresaRequestDto.getPermissaoEditor(), "ADICIONAR_EMPRESA");
        empresaRepository.existsByCnpj(empresaRequestDto.getCnpj());
        Empresa empresaCadastrada = empresaRepository.save(
            EmpresaMapper.toEntity(empresaRequestDto)
        );
        salaNotifier.onEmpresaCriada(empresaCadastrada, usuarioRepository.findById(empresaRequestDto.getIdEditor()).get());
        return empresaResponseMapper.toResponse(empresaCadastrada);
    }
}

