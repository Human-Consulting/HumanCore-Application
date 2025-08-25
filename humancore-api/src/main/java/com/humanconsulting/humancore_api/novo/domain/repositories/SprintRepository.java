package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.Sprint;
import java.util.List;

public interface SprintRepository {
    Sprint save(Sprint sprint);
    Sprint findById(Integer id);
    List<Sprint> findByProjeto_IdProjeto(Integer idProjeto);
}

