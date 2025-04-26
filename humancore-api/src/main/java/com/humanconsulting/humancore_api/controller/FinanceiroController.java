package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.financeiro.AtualizarFinanceiroRequestDto;
import com.humanconsulting.humancore_api.controller.dto.financeiro.FinanceiroRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.financeiro.FinanceiroResponseDto;
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
    public ResponseEntity<FinanceiroResponseDto> cadastrarFinanceiro(@Valid @RequestBody FinanceiroRequestDto financeiroRequestDto) {
        return ResponseEntity.status(201).body(service.cadastrar(financeiroRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<FinanceiroResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }

    @GetMapping("/buscarPorProjeto/{idProjeto}")
    public ResponseEntity<List<FinanceiroResponseDto>> listarPorId(@PathVariable Integer idProjeto) {
        return ResponseEntity.status(200).body(service.listarPorProjeto(idProjeto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanceiroResponseDto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{idFinanceiro}")
    public ResponseEntity<Financeiro> atualizar(
            @PathVariable
            Integer idFinanceiro,
            @Valid
            @RequestBody
            AtualizarFinanceiroRequestDto request) {
        Financeiro financeiroAtualizado = service.atualizar(idFinanceiro, request);

        return ResponseEntity.status(200).body(financeiroAtualizado);
    }
}
