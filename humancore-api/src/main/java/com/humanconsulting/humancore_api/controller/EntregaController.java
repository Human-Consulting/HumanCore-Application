package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.entrega.AtualizarGeralRequestDto;
import com.humanconsulting.humancore_api.controller.dto.atualizar.entrega.AtualizarStatusRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.EntregaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.EntregaResponseDto;
import com.humanconsulting.humancore_api.service.EntregaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("entregas")
@CrossOrigin("*")
public class EntregaController {

    @Autowired
    private EntregaService service;

    @PostMapping
    public ResponseEntity<EntregaResponseDto> cadastrarEntrega(@Valid @RequestBody EntregaRequestDto entregaRequestDto) {
        return ResponseEntity.status(201).body(service.cadastrar(entregaRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<EntregaResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }

    @GetMapping("/{idEntrega}")
    public ResponseEntity<EntregaResponseDto> buscarPorId(@PathVariable Integer id) {
        return ResponseEntity.status(200).body(service.buscarPorId(id));
    }

    @DeleteMapping("/{idEntrega}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idEntrega) {
        service.deletar(idEntrega);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{idEntrega}")
    public ResponseEntity<EntregaResponseDto> atualizar(@PathVariable Integer idEntrega, @Valid @RequestBody AtualizarGeralRequestDto entrega) {
        return ResponseEntity.status(200).body(service.atualizar(idEntrega, entrega));
    }

    @PutMapping("/finalizada/{idEntrega}")
    public ResponseEntity<EntregaResponseDto> atualizarFinalizada(@PathVariable Integer idEntrega, @RequestBody AtualizarStatusRequestDto request) {
        return ResponseEntity.status(200).body(service.atualizarFinalizada(idEntrega, request));
    }

    @PutMapping("/impedimento/{idEntrega}")
    public ResponseEntity<EntregaResponseDto> atualizarImpedimento(@PathVariable Integer idEntrega, @RequestBody AtualizarStatusRequestDto request) {
        return ResponseEntity.status(200).body(service.atualizarImpedimento(idEntrega, request));
    }
}
