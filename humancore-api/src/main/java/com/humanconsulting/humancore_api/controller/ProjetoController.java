package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.projeto.ProjetoAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.ProjetoRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.ProjetoResponseDto;
import com.humanconsulting.humancore_api.model.Projeto;
import com.humanconsulting.humancore_api.service.ProjetoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("projetos")
@CrossOrigin("*")
public class ProjetoController {

    @Autowired
    private ProjetoService service;

    @PostMapping
    public ResponseEntity<ProjetoResponseDto> cadastrarProjeto(@Valid @RequestBody ProjetoRequestDto projetoRequestDto) {
        return ResponseEntity.status(201).body(service.cadastrar(projetoRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<ProjetoResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Projeto> buscarPorId(@PathVariable Integer id) {
        Projeto projeto = service.buscarPorId(id);
        return ResponseEntity.status(200).body(projeto);
    }

    @GetMapping("/buscarPorEmpresa/{idEmpresa}")
    public ResponseEntity<List<ProjetoResponseDto>> buscarPorIdEmpresa(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(200).body(service.buscarPorIdEmpresa(idEmpresa));
    }

    @DeleteMapping("/{idProjeto}")
    public ResponseEntity<Void> deletar(@PathVariable Integer idProjeto) {
        service.deletar(idProjeto);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{idProjeto}")
    public ResponseEntity<ProjetoResponseDto> atualizar(@PathVariable Integer idProjeto, @Valid @RequestBody ProjetoAtualizarRequestDto request) {
        return ResponseEntity.status(200).body(service.atualizar(idProjeto, request));
    }
}
