package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.EmpresaResponseDto;
import com.humanconsulting.humancore_api.model.Empresa;
import com.humanconsulting.humancore_api.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("empresas")
public class EmpresaController {

    @Autowired
    private EmpresaService service;

    @PostMapping
    public ResponseEntity<EmpresaResponseDto> cadastrarEmpresa(@Valid @RequestBody Empresa empresa) {
        Empresa empresaCadastrada = service.cadastrar(empresa);
        return ResponseEntity.status(201).body(EmpresaResponseDto.toResponse(empresaCadastrada));
    }

    @GetMapping
    public ResponseEntity<List<EmpresaResponseDto>> listar() {
        List<Empresa> all = service.listar();

        List<EmpresaResponseDto> empresasResponse = all.stream()
                .map(u -> EmpresaResponseDto.toResponse(u))
                .toList();

        return ResponseEntity.status(200).body(empresasResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmpresaResponseDto> buscarPorId(@PathVariable Integer id) {
        Empresa empresa = service.buscarPorId(id);
        return ResponseEntity.status(200).body(EmpresaResponseDto.toResponse(empresa));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaResponseDto> atualizar(
            @PathVariable Integer idEmpresa,

            @Valid
            @RequestBody Empresa empresa) {
        Empresa empresaAtualizada = service.atualizar(idEmpresa, empresa);

        return ResponseEntity.status(200).body(EmpresaResponseDto.toResponse(empresaAtualizada));
    }
}
