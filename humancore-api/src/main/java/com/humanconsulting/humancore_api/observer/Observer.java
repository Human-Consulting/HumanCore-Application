package com.humanconsulting.humancore_api.observer;

import com.humanconsulting.humancore_api.model.Tarefa;

public interface Observer {
    void update(Tarefa tarefa);
}
