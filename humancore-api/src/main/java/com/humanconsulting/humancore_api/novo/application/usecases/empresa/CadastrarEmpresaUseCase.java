package com.humanconsulting.humancore_api.novo.application.usecases.empresa;

import com.humanconsulting.humancore_api.novo.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.novo.domain.repositories.UsuarioRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.novo.web.dtos.response.empresa.EmpresaResponseDto;

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
        PermissaoValidator.validarPermissao(empresaRequestDto.getPermissaoEditor(), "ADICIONAR_EMPRESA");
        empresaRepository.existsByCnpj(empresaRequestDto.getCnpj());
        Empresa empresaCadastrada = empresaRepository.save(
            EmpresaResponseMapper.toEntity(empresaRequestDto)
        );
        salaNotifier.onEmpresaCriada(empresaCadastrada, usuarioRepository.findById(empresaRequestDto.getIdEditor()).get());
        return empresaResponseMapper.toResponse(empresaCadastrada);
    }
}

