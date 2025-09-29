package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;

public class DeletarProjetoUseCase {
    private final ProjetoRepository projetoRepository;
    private final BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase;

    public DeletarProjetoUseCase(ProjetoRepository projetoRepository, BuscarProjetoPorIdUseCase buscarProjetoPorIdUseCase) {
        this.projetoRepository = projetoRepository;
        this.buscarProjetoPorIdUseCase = buscarProjetoPorIdUseCase;
    }

    public void execute(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        ValidarPermissao.execute(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_PROJETO");
        buscarProjetoPorIdUseCase.execute(id);
        projetoRepository.deleteById(id);
    }
}

