package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.tarefa.AtualizarStatusRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.TarefaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.TarefaResponseDto;
import com.humanconsulting.humancore_api.service.TarefaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("tarefas")
@CrossOrigin("*")
public class EntregaController {

    @Autowired
    private TarefaService service;

    @PostMapping
    public ResponseEntity<TarefaResponseDto> cadastrarEntrega(@Valid @RequestBody TarefaRequestDto entregaRequestDto) {
        return ResponseEntity.status(201).body(service.cadastrar(entregaRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<TarefaResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }

    @GetMapping("/buscarPorSprint/{idSprint}")
    public ResponseEntity<List<TarefaResponseDto>> listarPorIdSprint(@PathVariable Integer idSprint) {
        return ResponseEntity.status(200).body(service.listarPorSprint(idSprint));
    }

    @GetMapping("/{idTarefa}")
    public ResponseEntity<TarefaResponseDto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @DeleteMapping("/{idTarefa}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idTarefa) {
        service.deletar(idTarefa);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{idTarefa}")
    public ResponseEntity<TarefaResponseDto> atualizar(@PathVariable Integer idTarefa, @Valid @RequestBody AtualizarGeralRequestDto entrega) {
        return ResponseEntity.status(200).body(service.atualizar(idTarefa, entrega));
    }

    @PutMapping("/finalizada/{idTarefa}")
    public ResponseEntity<TarefaResponseDto> atualizarFinalizada(@PathVariable Integer idTarefa, @RequestBody AtualizarStatusRequestDto request) {
        return ResponseEntity.status(200).body(service.atualizarFinalizada(idTarefa, request));
    }

    @PutMapping("/impedimento/{idTarefa}")
    public ResponseEntity<TarefaResponseDto> atualizarImpedimento(@PathVariable Integer idTarefa, @RequestBody AtualizarStatusRequestDto request) {
        return ResponseEntity.status(200).body(service.atualizarImpedimento(idTarefa, request));
    }
}
