package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.model.Sprint;
import com.humanconsulting.humancore_api.service.SprintService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sprints")
public class SprintController {

    @Autowired
    private SprintService service;

    @PostMapping
    public ResponseEntity<Sprint> cadastrarSprint(@Valid @RequestBody Sprint sprint) {
        Sprint sprintCadastrada = service.cadastrar(sprint);
        return ResponseEntity.status(201).body(sprintCadastrada);
    }

    @GetMapping
    public ResponseEntity<List<Sprint>> listar() {
        List<Sprint> all = service.listar();
        return ResponseEntity.status(200).body(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sprint> buscarPorId(@PathVariable Integer id) {
        Sprint sprint = service.buscarPorId(id);
        return ResponseEntity.status(200).body(sprint);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }
}
