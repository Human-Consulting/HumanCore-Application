package com.humanconsulting.humancore_api.novo.application.usecases.empresa;

import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.novo.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.novo.web.dtos.request.UsuarioPermissaoDto;
import java.util.Optional;

public class DeletarEmpresaUseCase {
    private final EmpresaRepository empresaRepository;

    public DeletarEmpresaUseCase(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public void execute(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        ValidarPermissao.execute(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_EMPRESA");
        Optional<Empresa> optEmpresa = empresaRepository.findById(id);
        if (optEmpresa.isEmpty()) throw new EntidadeNaoEncontradaException("EmpresaEntity não encontrada.");
        empresaRepository.deleteById(id);
    }
}