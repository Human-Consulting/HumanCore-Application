package com.humanconsulting.humancore_api.novo.application.usecases.investimento;

import com.humanconsulting.humancore_api.novo.domain.repositories.InvestimentoRepository;
import com.humanconsulting.humancore_api.novo.web.dtos.request.UsuarioPermissaoDto;

public class DeletarInvestimentoUseCase {
    private final InvestimentoRepository investimentoRepository;

    public DeletarInvestimentoUseCase(InvestimentoRepository investimentoRepository) {
        this.investimentoRepository = investimentoRepository;
    }

    public void execute(Integer id, UsuarioPermissaoDto usuarioPermissaoDto) {
        PermissaoValidator.validarPermissao(usuarioPermissaoDto.getPermissaoEditor(), "EXCLUIR_INVESTIMENTO");
        investimentoRepository.deleteById(id);
    }
}

