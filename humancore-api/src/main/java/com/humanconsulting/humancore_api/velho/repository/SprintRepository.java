package com.humanconsulting.humancore_api.velho.repository;

import com.humanconsulting.humancore_api.velho.model.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SprintRepository extends JpaRepository<Sprint, Integer> {
    List<Sprint> findByProjeto_IdProjeto(Integer idProjeto);
}
