package com.humanconsulting.humancore_api.application.usecases.investimento;

import com.humanconsulting.humancore_api.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.domain.security.ValidarPermissao;
import com.humanconsulting.humancore_api.web.dtos.request.UsuarioPermissaoDto;

public class DeletarInvestimentoUseCase {
    private final InvestimentoRepository investimentoRepository;

    public DeletarInvestimentoUseCase(InvestimentoRepository investimentoRepository) {
        this.investimentoRepository = investimentoRepository;
    }

    public void execute(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        ValidarPermissao.execute(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_INVESTIMENTO");
        investimentoRepository.deleteById(id);
    }
}

