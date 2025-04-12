package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.EmpresaResponseDto;
import com.humanconsulting.humancore_api.mapper.EmpresaMapper;
import com.humanconsulting.humancore_api.model.Empresa;
import com.humanconsulting.humancore_api.service.EmpresaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("empresas")
@CrossOrigin("*")
public class EmpresaController {

    @Autowired
    private EmpresaService service;

    @PostMapping
    public ResponseEntity<EmpresaResponseDto> cadastrarEmpresa(@Valid @RequestBody EmpresaRequestDto empresaRequestDto) {
        return ResponseEntity.status(201).body(service.cadastrar(empresaRequestDto));
    }

    @GetMapping
    public ResponseEntity<List<EmpresaResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.status(204).build();
    }

    @PutMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaResponseDto> atualizar(@PathVariable Integer idEmpresa, @Valid @RequestBody EmpresaAtualizarRequestDto empresa) {
        return ResponseEntity.status(200).body(service.atualizar(idEmpresa, empresa));
    }
}
