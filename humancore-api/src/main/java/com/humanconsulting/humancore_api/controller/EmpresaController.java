package com.humanconsulting.humancore_api.controller;

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
    public ResponseEntity<Empresa> cadastrarEmpresa(@Valid @RequestBody Empresa empresa) {
        Empresa empresaCadastrada = service.cadastrar(empresa);
        return ResponseEntity.status(201).body(empresaCadastrada);
    }

    @GetMapping
    public ResponseEntity<List<Empresa>> listar() {
        List<Empresa> all = service.listar();
        return ResponseEntity.status(200).body(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empresa> buscarPorId(@PathVariable Integer id) {
        Empresa empresa = service.buscarPorId(id);
        return ResponseEntity.status(200).body(empresa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }
}
