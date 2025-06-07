package com.humanconsulting.humancore_api.observer;

import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.model.Sala;
import com.humanconsulting.humancore_api.repository.SalaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjetoNotifier implements ProjetoObserver {

    private final List<ProjetoObserver> observers;
    private final SalaRepository salaRepository;

    @Autowired
    public ProjetoNotifier(List<ProjetoObserver> observers, SalaRepository salaRepository) {
        this.observers = observers;
        this.salaRepository = salaRepository;
    }

    @Override
    public void onProjetoCriado(Projeto projeto) {
        Sala novaSala = new Sala();
        novaSala.setProjeto(projeto);
        novaSala.setNome("Sala do projeto: " + projeto.getTitulo());
        salaRepository.save(novaSala);

        for (ProjetoObserver observer : observers) {
            observer.onProjetoCriado(projeto);
        }
    }
}