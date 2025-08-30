package com.humanconsulting.humancore_api.novo.application.usecases.projeto;

import com.humanconsulting.humancore_api.novo.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.request.UsuarioPermissaoDto;

public class DeletarProjetoUseCase {
    private final ProjetoRepository projetoRepository;
    private final BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase;

    public DeletarProjetoUseCase(ProjetoRepository projetoRepository, BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase) {
        this.projetoRepository = projetoRepository;
        this.buscarProjetoPorIdUseCase = buscarProjetoPorIdUseCase;
    }

    public void execute(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        PermissaoValidator.validarPermissao(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_PROJETO");
        buscarProjetoPorIdUseCase.execute(id);
        projetoRepository.deleteById(id);
    }
}

