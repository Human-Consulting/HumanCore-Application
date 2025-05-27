package com.humanconsulting.humancore_api.controller;

import com.humanconsulting.humancore_api.controller.dto.atualizar.empresa.EmpresaAtualizarRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.EmpresaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.request.UsuarioPermissaoDto;
import com.humanconsulting.humancore_api.controller.dto.response.empresa.DashboardEmpresaResponseDto;
import com.humanconsulting.humancore_api.controller.dto.response.empresa.EmpresaResponseDto;
import com.humanconsulting.humancore_api.service.EmpresaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    @Operation(summary = "Cadastrar uma nova empresa",
            description = "Esse endpoint cria uma nova empresa no sistema",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Empresa cadastrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para o cadastro")
    })
    @PostMapping
    public ResponseEntity<EmpresaResponseDto> cadastrarEmpresa(@Valid @RequestBody EmpresaRequestDto empresaRequestDto) {
        return ResponseEntity.status(201).body(service.cadastrar(empresaRequestDto));
    }

    @Operation(summary = "Listar todas as empresas",
            description = "Esse endpoint retorna uma lista com todas as empresas cadastradas",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponse(responseCode = "200", description = "Lista de empresas retornada com sucesso")
    @GetMapping
    public ResponseEntity<List<EmpresaResponseDto>> listar() {
        return ResponseEntity.status(200).body(service.listar());
    }

    @Operation(summary = "Buscar empresa por ID",
            description = "Esse endpoint retorna os detalhes de uma empresa específica com base no ID fornecido",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    @GetMapping("/{idEmpresa}")
    public ResponseEntity<DashboardEmpresaResponseDto> buscarPorId(@PathVariable Integer idEmpresa) {
        return ResponseEntity.status(200).body(service.criarDashboardResponse(service.buscarPorId(idEmpresa)));
    }

    @Operation(summary = "Deletar uma empresa",
            description = "Esse endpoint remove uma empresa do sistema com base no ID fornecido",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Empresa deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id, @RequestBody UsuarioPermissaoDto usuarioPermissaoDto) {
        service.deletar(id, usuarioPermissaoDto);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "Atualizar dados de uma empresa",
            description = "Esse endpoint atualiza os dados de uma empresa existente",
            security = @SecurityRequirement(name = "Bearer")
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Empresa atualizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Empresa não encontrada")
    })
    @PatchMapping("/{idEmpresa}")
    public ResponseEntity<EmpresaResponseDto> atualizar(@PathVariable Integer idEmpresa, @Valid @RequestBody EmpresaAtualizarRequestDto request) {
        return ResponseEntity.status(200).body(service.atualizar(idEmpresa, request));
    }
}
