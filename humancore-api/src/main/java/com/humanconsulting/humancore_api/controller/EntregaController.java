package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.EntregaAtualizarRequestDto;
import com.humanconsulting.humancore_api.model.Entrega;
import com.humanconsulting.humancore_api.service.EntregaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("entregas")
public class EntregaController {

    @Autowired
    private EntregaService service;

    @PostMapping
    public ResponseEntity<Entrega> cadastrarEntrega(@Valid @RequestBody Entrega entrega) {
        Entrega entregaCadastrada = service.cadastrar(entrega);
        return ResponseEntity.status(201).body(entregaCadastrada);
    }

    @GetMapping
    public ResponseEntity<List<Entrega>> listar() {
        List<Entrega> all = service.listar();
        return ResponseEntity.status(200).body(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Entrega> buscarPorId(@PathVariable Integer id) {
        Entrega entrega = service.buscarPorId(id);
        return ResponseEntity.status(200).body(entrega);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{idEntrega}")
    public ResponseEntity<Entrega> atualizar(
            @PathVariable Integer idEntrega,

            @Valid
            @RequestBody EntregaAtualizarRequestDto entrega) {
        Entrega entregaAtualizada = service.atualizar(idEntrega, entrega);

        return ResponseEntity.status(200).body(entregaAtualizada);
    }

    @PatchMapping("/finalizada/{id}")
    public ResponseEntity<Entrega> atualizarFinalizada(
            @PathVariable Integer id,
            @RequestBody Boolean novoFinalizada
    ) {
        Entrega entregaAtualizada = service.atualizarFinalizada(id, novoFinalizada);

        return ResponseEntity.status(200).body(entregaAtualizada);
    }

    @PatchMapping("/impedimento/{id}")
    public ResponseEntity<Entrega> atualizarImpedimento(
            @PathVariable Integer id,
            @RequestBody Boolean impedimento
    ) {
        Entrega entregaAtualizada = service.atualizarImpedimento(id, impedimento);

        return ResponseEntity.status(200).body(entregaAtualizada);
    }

    @PatchMapping("/progresso/{id}")
    public ResponseEntity<Entrega> atualizarProgresso(
            @PathVariable Integer id,
            @RequestBody Double progresso
    ) {
        Entrega entregaAtualizada = service.atualizarProgresso(id, progresso);

        return ResponseEntity.status(200).body(entregaAtualizada);
    }
}
