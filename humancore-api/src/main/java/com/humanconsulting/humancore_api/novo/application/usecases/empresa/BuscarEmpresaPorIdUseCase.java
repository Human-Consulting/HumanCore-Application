package com.humanconsulting.humancore_api.novo.application.usecases.empresa;

import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.repositories.EmpresaRepository;

public class BuscarEmpresaPorIdUseCase {
    private final EmpresaRepository empresaRepository;

    public BuscarEmpresaPorIdUseCase(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public Empresa execute(Integer id) {
        Empresa empresa = empresaRepository.findById(id);
        if (empresa == null) {
            throw new EntidadeNaoEncontradaException("EmpresaEntity não encontrada.");
        }
        return empresa;
    }
}

