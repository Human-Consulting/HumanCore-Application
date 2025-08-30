package com.humanconsulting.humancore_api.novo.application.usecases.empresa;

import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.repositories.EmpresaRepository;

public class BuscarEmpresaPorIdUseCase {
    private final EmpresaRepository empresaRepository;

    public BuscarEmpresaPorIdUseCase(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public Empresa execute(Integer id) {
        return empresaRepository.findById(id)
                .orElseThrow(() -> new EntidadeNaoEncontradaException("EmpresaEntity não encontrada."));
    }
}

