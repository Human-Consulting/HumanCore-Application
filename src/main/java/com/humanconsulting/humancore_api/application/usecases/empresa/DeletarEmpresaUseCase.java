package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.exception.EntidadeNaoEncontradaException;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;
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