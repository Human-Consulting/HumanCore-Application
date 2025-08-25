package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.MensagemInfo;
import com.humanconsulting.humancore_api.novo.domain.entities.Sala;
import java.util.List;

public interface MensagemInfoRepository {
    List<MensagemInfo> findBySalaOrderByHorarioAsc(Sala sala);

    MensagemInfo save(MensagemInfo mensagemInfo);
    MensagemInfo findById(Integer id);
    List<MensagemInfo> findAll();
    void deleteById(Integer id);
}
