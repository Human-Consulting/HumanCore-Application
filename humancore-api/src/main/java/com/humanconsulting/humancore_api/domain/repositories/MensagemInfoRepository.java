package com.humanconsulting.humancore_api.domain.repositories;

import com.humanconsulting.humancore_api.domain.entities.MensagemInfo;
import com.humanconsulting.humancore_api.domain.entities.Sala;

import java.util.List;

public interface MensagemInfoRepository {
    List<MensagemInfo> findBySalaOrderByHorarioAsc(Sala sala);

    MensagemInfo save(MensagemInfo mensagemInfo);
    MensagemInfo findById(Integer id);
    List<MensagemInfo> findAll();
    void deleteById(Integer id);
}
