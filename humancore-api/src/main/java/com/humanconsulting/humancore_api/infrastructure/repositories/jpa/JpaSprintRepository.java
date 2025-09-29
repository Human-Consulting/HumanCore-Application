package com.humanconsulting.humancore_api.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.infrastructure.entities.SprintEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaSprintRepository extends JpaRepository<SprintEntity, Integer> {
    List<SprintEntity> findByProjeto_IdProjeto(Integer idProjeto);
}
