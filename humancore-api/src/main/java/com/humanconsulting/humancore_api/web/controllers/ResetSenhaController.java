package com.humanconsulting.humancore_api.web.controllers;

import com.humanconsulting.humancore_api.application.usecases.usuario.reset.ConfirmarResetSenhaUseCase;
import com.humanconsulting.humancore_api.application.usecases.usuario.reset.SolicitarResetSenhaUseCase;
import com.humanconsulting.humancore_api.web.dtos.request.ResetSenhaRequest;
import com.humanconsulting.humancore_api.web.dtos.request.SolicitarResetSenhaRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/reset-senha")
public class ResetSenhaController {

    @Autowired
    private SolicitarResetSenhaUseCase solicitarResetSenhaUseCase;

    @Autowired
    private ConfirmarResetSenhaUseCase confirmarResetSenhaUseCase;

    @PostMapping("/solicitar")
    public ResponseEntity<Void> solicitar(@Valid @RequestBody SolicitarResetSenhaRequest request) {
        solicitarResetSenhaUseCase.execute(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/confirmar")
    public ResponseEntity<Void> confirmar(@Valid @RequestBody ResetSenhaRequest request) {
        confirmarResetSenhaUseCase.execute(request);
        return ResponseEntity.ok().build();
    }
}

