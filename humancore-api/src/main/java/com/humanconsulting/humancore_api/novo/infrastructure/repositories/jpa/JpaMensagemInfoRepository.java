package com.humanconsulting.humancore_api.novo.infrastructure.repositories.jpa;

import com.humanconsulting.humancore_api.velho.model.MensagemInfo;
import com.humanconsulting.humancore_api.velho.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaMensagemInfoRepository extends JpaRepository<MensagemInfo, Integer> {

    List<MensagemInfo> findBySalaOrderByHorarioAsc(Sala sala);
}
