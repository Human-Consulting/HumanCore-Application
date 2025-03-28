package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.projeto.AtualizarFinalizadaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.projeto.AtualizarProgressoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projetos")
public class ProjetoController {

    @Autowired
    private ProjetoService service;

    @PostMapping
    public ResponseEntity<Projeto> cadastrarProjeto(@Valid @RequestBody Projeto projeto) {
        Projeto projetoCadastrado = service.cadastrar(projeto);
        return ResponseEntity.status(201).body(projetoCadastrado);
    }

    @GetMapping
    public ResponseEntity<List<Projeto>> listar() {
        List<Projeto> all = service.listar();
        return ResponseEntity.status(200).body(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscarPorId(@PathVariable Integer id) {
        Projeto projeto = service.buscarPorId(id);
        return ResponseEntity.status(200).body(projeto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{idProjeto}")
    public ResponseEntity<Projeto> atualizar(
            @PathVariable Integer idProjeto,

            @Valid
            @RequestBody ProjetoAtualizarRequestDto request) {
        Projeto projetoAtualizado = service.atualizar(idProjeto, request);

        return ResponseEntity.status(200).body(projetoAtualizado);
    }

    @PatchMapping("/progresso/{id}")
    public ResponseEntity<Projeto> atualizarProgresso(
            @PathVariable Integer id,
            @RequestBody AtualizarProgressoRequestDto request
    ) {
        Projeto projetoAtualizado = service.atualizarProgresso(id, request);

        return ResponseEntity.status(200).body(projetoAtualizado);
    }

    @PatchMapping("/impedimento/{id}")
    public ResponseEntity<Projeto> atualizarImpedimento(
            @PathVariable Integer id,
            @RequestBody AtualizarFinalizadaRequestDto request
    ) {
        Projeto projetoAtualizado = service.atualizarImpedimento(id, request);

        return ResponseEntity.status(200).body(projetoAtualizado);
    }
}
