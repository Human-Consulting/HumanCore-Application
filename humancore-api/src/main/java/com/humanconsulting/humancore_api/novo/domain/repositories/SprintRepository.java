package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.Sprint;
import java.util.List;
import java.util.Optional;

public interface SprintRepository {
    Sprint save(Sprint sprint);
    Optional<Sprint> findById(Integer id);
    List<Sprint> findAll();
    void deleteById(Integer id);
    List<Sprint> findByProjeto_IdProjeto(Integer idProjeto);
}

