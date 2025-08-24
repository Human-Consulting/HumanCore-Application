package com.humanconsulting.humancore_api.velho.repository;

import com.humanconsulting.humancore_api.velho.model.MensagemInfo;
import com.humanconsulting.humancore_api.velho.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemInfoRepository extends JpaRepository<MensagemInfo, Integer> {

    List<MensagemInfo> findBySalaOrderByHorarioAsc(Sala sala);
}
