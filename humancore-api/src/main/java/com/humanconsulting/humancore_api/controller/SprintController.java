package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.sprint.*;
import com.humanconsulting.humancore_api.controller.dto.request.SprintRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.SprintResponseDto;
import com.humanconsulting.humancore_api.model.Sprint;
import com.humanconsulting.humancore_api.service.SprintService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("sprints")
@CrossOrigin("*")
public class SprintController {

    @Autowired
    private SprintService service;

    @PostMapping
    public ResponseEntity<SprintResponseDto> cadastrarSprint(@Valid @RequestBody SprintRequestDto sprintRequestDto) {
        return ResponseEntity.status(201).body(service.cadastrar(sprintRequestDto));
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

    @GetMapping("/buscarPorProjeto/{id}")
    public ResponseEntity<List<SprintResponseDto>> buscarPorIdEmpresa(@PathVariable Integer idProjeto) {
        return ResponseEntity.status(200).body(service.buscarPorIdProjeto(idProjeto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{idSprint}")
    public ResponseEntity<SprintResponseDto> atualizar(@PathVariable Integer idSprint, @Valid @RequestBody SprintAtualizarRequestDto request) {
        return ResponseEntity.status(200).body(service.atualizar(idSprint, request));
    }
}
