package com.humanconsulting.humancore_api.novo.application.usecases.projeto;

import com.humanconsulting.humancore_api.novo.domain.entities.Projeto;
import com.humanconsulting.humancore_api.novo.domain.repositories.ProjetoRepository;

import java.util.Optional;

public class BuscarProjetoPorIdUseCase {
    private final ProjetoRepository projetoRepository;

    public BuscarProjetoPorIdUseCase(ProjetoRepository projetoRepository) {
        this.projetoRepository = projetoRepository;
    }

    public Projeto execute(Integer id) {
        Optional<Projeto> optProjeto = projetoRepository.findById(id);
        if (optProjeto.isEmpty()) throw new EntidadeSemRetornoException("Nenhum projeto encontrado.");
        return optProjeto.get();
    }
}

