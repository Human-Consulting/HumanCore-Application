package com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.novo.infrastructure.entities.MensagemInfoEntity;
import com.humanconsulting.humancore_api.novo.infrastructure.entities.SalaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaMensagemInfoRepository extends JpaRepository<MensagemInfoEntity, Integer> {

    List<MensagemInfoEntity> findBySalaOrderByHorarioAsc(SalaEntity sala);
}
