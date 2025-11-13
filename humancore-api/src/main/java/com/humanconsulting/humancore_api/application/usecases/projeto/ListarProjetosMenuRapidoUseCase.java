package com.humanconsulting.humancore_api.application.usecases.projeto;

import com.humanconsulting.humancore_api.application.usecases.projeto.mappers.ProjetoResponseMapper;
import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.exception.EntidadeSemRetornoException;
import com.humanconsulting.humancore_api.domain.repositories.ProjetoRepository;
import com.humanconsulting.humancore_api.domain.utils.PageResult;
import com.humanconsulting.humancore_api.infrastructure.utils.PageResultImpl;
import com.humanconsulting.humancore_api.web.dtos.response.projeto.ProjetoResponseDto;

import java.util.List;
import java.util.stream.Collectors;

public class ListarProjetosMenuRapidoUseCase {
    private final ProjetoRepository projetoRepository;
    private final ProjetoResponseMapper projetoResponseMapper;

    public ListarProjetosMenuRapidoUseCase(ProjetoRepository projetoRepository, ProjetoResponseMapper projetoResponseMapper) {
        this.projetoRepository = projetoRepository;
        this.projetoResponseMapper = projetoResponseMapper;
    }

    public PageResult<ProjetoResponseDto> execute(Integer idEmpresa, int page, int size, String nome, Boolean impedidos, Boolean concluidos) {
        PageResult<Projeto> projetos;

        boolean temFiltro = (impedidos != null && impedidos) ||
                (concluidos != null && concluidos);

        if (nome != null && !nome.isEmpty()) {
            projetos = projetoRepository.findAllByEmpresa_IdEmpresaAndNomeContainingIgnoreCase(idEmpresa, page, size, nome);
        } else {
            projetos = projetoRepository.findAllByEmpresa_IdEmpresa(idEmpresa, page, size);
        }

        if (projetos.getContent().isEmpty())
            throw new EntidadeSemRetornoException("Nenhuma projeto registrada");

        List<ProjetoResponseDto> filtradas = projetos.getContent().stream()
                .map(projetoResponseMapper::toResponseMenuRapido)
                .filter(dto -> (impedidos == null || !impedidos || dto.isComImpedimento()))
                .filter(dto -> (concluidos == null || !concluidos || dto.getProgresso() == 100))
                .collect(Collectors.toList());

        if (!temFiltro) {
            return new PageResultImpl<>(
                    filtradas,
                    projetos.getPageNumber(),
                    projetos.getPageSize(),
                    projetos.getTotalElements(),
                    projetos.getTotalPages()
            );
        }

        int fromIndex = Math.min(page * size, filtradas.size());
        int toIndex = Math.min(fromIndex + size, filtradas.size());
        List<ProjetoResponseDto> paginaFiltrada = filtradas.subList(fromIndex, toIndex);

        return new PageResultImpl<>(
                paginaFiltrada,
                page,
                size,
                filtradas.size(),
                (int) Math.ceil((double) filtradas.size() / size)
        );
    }
}
