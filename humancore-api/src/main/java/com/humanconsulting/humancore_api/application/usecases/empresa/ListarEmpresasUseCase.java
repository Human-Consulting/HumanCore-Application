package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.utils.PageResult;
import com.humanconsulting.humancore_api.infrastructure.utils.PageResultImpl;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;

import java.util.ArrayList;
import java.util.List;

public class ListarEmpresasUseCase {
    private final EmpresaRepository empresaRepository;
    private final EmpresaResponseMapper empresaResponseMapper;

    public ListarEmpresasUseCase(EmpresaRepository empresaRepository, EmpresaResponseMapper empresaResponseMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaResponseMapper = empresaResponseMapper;
    }

    public PageResult<EmpresaResponseDto> execute(int page, int size, String nome) {
        PageResult<Empresa> empresas = null;
        if (nome != null && !nome.isEmpty()) empresas = empresaRepository.findAllByNomeContainingIgnoreCase(page, size, nome);
        else empresas = empresaRepository.findAll(page, size);

        if (empresas.getContent().isEmpty()) throw new EntidadeSemRetornoException("Nenhuma empresa registrada");
        List<EmpresaResponseDto> allResponse = new ArrayList<>();
        for (Empresa empresa : empresas.getContent()) {
            /*String urlImagem = empresaRepository.findUrlImagemById(empresa.getIdEmpresa());
            empresa.setUrlImagem(urlImagem);*/
            allResponse.add(empresaResponseMapper.toResponse(empresa));
        }

        return new PageResultImpl<>(
                allResponse,
                empresas.getPageNumber(),
                empresas.getPageSize(),
                empresas.getTotalElements(),
                empresas.getTotalPages()
        );
    }
}

