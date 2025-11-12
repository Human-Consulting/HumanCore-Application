package com.humanconsulting.humancore_api.domain.repositories;

import com.humanconsulting.humancore_api.domain.entities.Sala;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.entities.Projeto;

import java.util.List;
import java.util.Optional;

public interface SalaRepository {
    Sala findByProjeto(Projeto projeto);
    Sala findByEmpresa(Empresa empresa);
    List<Sala> findSalasComUsuariosPorUsuario(Integer idUsuario);
    Optional<Sala> buscarComUsuarios(Integer idSala);

    Sala save(Sala sala);
    Optional<Sala> findById(Integer id);
    List<Sala> findAll();
    void deleteById(Integer id);
}
