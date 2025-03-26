package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.model.Financeiro;
import com.humanconsulting.humancore_api.service.FinanceiroService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("financeiros")
public class FinanceiroController {

    @Autowired
    private FinanceiroService service;

    @PostMapping
    public ResponseEntity<Financeiro> cadastrarFinanceiro(@Valid @RequestBody Financeiro financeiro) {
        Financeiro financeiroCadastrada = service.cadastrar(financeiro);
        return ResponseEntity.status(201).body(financeiroCadastrada);
    }

    @GetMapping
    public ResponseEntity<List<Financeiro>> listar() {
        List<Financeiro> all = service.listar();
        return ResponseEntity.status(200).body(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Financeiro> buscarPorId(@PathVariable Integer id) {
        Financeiro financeiro = service.buscarPorId(id);
        return ResponseEntity.status(200).body(financeiro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{idFinanceiro}")
    public ResponseEntity<Financeiro> atualizar(
            @PathVariable Integer idFinanceiro,

            @Valid
            @RequestBody Financeiro financeiro) {
        Financeiro financeiroAtualizado = service.atualizar(idFinanceiro, financeiro);

        return ResponseEntity.status(200).body(financeiroAtualizado);
    }
}
