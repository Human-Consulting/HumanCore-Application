package com.humanconsulting.humancore_api.utils;

import com.humanconsulting.humancore_api.model.Checkpoint;

import java.util.List;

public class ProgressoCalculator {

    public static Double calularProgresso(List<Checkpoint> checkpoint) {
        int total = checkpoint.size();
        long finalizados = checkpoint.stream()
                .filter(Checkpoint::getFinalizado)
                .count();
        return total > 0 ? (finalizados * 100) / total : 0.0;
    }
}
