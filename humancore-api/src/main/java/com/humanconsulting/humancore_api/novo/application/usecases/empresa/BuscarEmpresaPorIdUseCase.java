package com.humanconsulting.humancore_api.novo.application.usecases.empresa;

import com.humanconsulting.humancore_api.novo.domain.entities.Empresa;
import com.humanconsulting.humancore_api.novo.domain.repositories.EmpresaRepository;

import java.util.Optional;

public class BuscarEmpresaPorIdUseCase {
    private final EmpresaRepository empresaRepository;

    public BuscarEmpresaPorIdUseCase(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public Empresa execute(Integer id) {
        Optional<Empresa> empresa = empresaRepository.findById(id);
        if (empresa.isEmpty()) {
            throw new EntidadeNaoEncontradaException("EmpresaEntity não encontrada.");
        }
        return empresa.orElse(null);

    }
}
