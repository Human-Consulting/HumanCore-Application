package com.humanconsulting.humancore_api.observer;

import com.humanconsulting.humancore_api.model.Entrega;

public interface Observer {
    void update(Entrega entrega);
}
