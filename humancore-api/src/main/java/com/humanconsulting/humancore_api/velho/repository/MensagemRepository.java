package com.humanconsulting.humancore_api.velho.repository;

import com.humanconsulting.humancore_api.velho.model.Mensagem;
import com.humanconsulting.humancore_api.velho.model.Sala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, Integer> {

    List<Mensagem> findBySalaOrderByHorarioAsc(Sala sala);
}
