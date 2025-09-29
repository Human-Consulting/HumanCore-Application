package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;

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

