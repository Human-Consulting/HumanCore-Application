package com.humanconsulting.humancore_api.novo.domain.repositories;

import com.humanconsulting.humancore_api.novo.domain.entities.Mensagem;
import com.humanconsulting.humancore_api.novo.domain.entities.Sala;
import java.util.List;

public interface MensagemRepository {
    List<Mensagem> findBySalaOrderByHorarioAsc(Sala sala);

    Mensagem save(Mensagem mensagem);
    List<Mensagem> findById(Integer id);
    List<Mensagem> findAll();
    void deleteById(Integer id);
}
