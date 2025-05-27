package com.humanconsulting.humancore_api.repository;

import com.humanconsulting.humancore_api.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Integer> {
    List<Sprint> findByProjeto_IdProjeto(Integer idProjeto);
}
