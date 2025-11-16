package com.humanconsulting.humancore_api.application.usecases.empresa;

import com.humanconsulting.humancore_api.application.usecases.empresa.mappers.EmpresaResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Empresa;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.EmpresaRepository;
import com.humanconsulting.humancore_api.domain.utils.PageResult;
import com.humanconsulting.humancore_api.infrastructure.utils.PageResultImpl;
import com.humanconsulting.humancore_api.web.dtos.response.empresa.EmpresaResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class ListarEmpresasMenuRapidoUseCase {
    private final EmpresaRepository empresaRepository;
    private final EmpresaResponseMapper empresaResponseMapper;

    public ListarEmpresasMenuRapidoUseCase(EmpresaRepository empresaRepository, EmpresaResponseMapper empresaResponseMapper) {
        this.empresaRepository = empresaRepository;
        this.empresaResponseMapper = empresaResponseMapper;
    }

    public PageResult<EmpresaResponseDto> execute(int page, int size, String nome, Boolean impedidos, Boolean concluidos) {
        PageResult<Empresa> empresas;

        boolean temFiltro = (impedidos != null && impedidos) ||
                (concluidos != null && concluidos);

        if (nome != null && !nome.isEmpty()) {
            empresas = empresaRepository.findAllByNomeContainingIgnoreCase(page, size, nome);
        } else {
            empresas = empresaRepository.findAll(page, size);
        }

        if (empresas.getContent().isEmpty())
            throw new EntidadeSemRetornoException("Nenhuma empresa registrada");

        List<EmpresaResponseDto> filtradas = empresas.getContent().stream()
                .map(empresaResponseMapper::toResponseMenuRapido)
                .filter(dto -> (impedidos == null || !impedidos || dto.isComImpedimento()))
                .filter(dto -> (concluidos == null || !concluidos || dto.getProgresso() == 100))
                .collect(Collectors.toList());

        if (!temFiltro) {
            return new PageResultImpl<>(
                    filtradas,
                    empresas.getPageNumber(),
                    empresas.getPageSize(),
                    empresas.getTotalElements(),
                    empresas.getTotalPages()
            );
        }

        int fromIndex = Math.min(page * size, filtradas.size());
        int toIndex = Math.min(fromIndex + size, filtradas.size());
        List<EmpresaResponseDto> paginaFiltrada = filtradas.subList(fromIndex, toIndex);

        return new PageResultImpl<>(
                paginaFiltrada,
                page,
                size,
                filtradas.size(),
                (int) Math.ceil((double) filtradas.size() / size)
        );
    }
}
