package com.humanconsulting.humancore_api.infrastructure.mappers;

import com.humanconsulting.humancore_api.domain.entities.Projeto;
import com.humanconsulting.humancore_api.domain.entities.Sprint;
import com.humanconsulting.humancore_api.domain.entities.Tarefa;
import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.web.dtos.response.email.EmailUpdateResponseDto;

public class EmailUpdateMapper {
    public static EmailUpdateResponseDto toEmailUpdateResponseDto(
            Tarefa tarefa,
            Sprint sprintTarefa,
            Projeto projetoTarefa,
            Usuario responsavelProjeto,
            Usuario responsavelTarefa) {
        EmailUpdateResponseDto dto = new EmailUpdateResponseDto();
        dto.setDescricaoProjeto(projetoTarefa.getDescricao());
        dto.setDescricaoSprint(sprintTarefa.getDescricao());
        dto.setDescricaoTarefa(tarefa.getDescricao());
        dto.setNomeResponsavelTarefa(responsavelTarefa.getNome());
        dto.setEmailResponsavelTarefa(responsavelTarefa.getEmail());
        dto.setNomeResponsavelProjeto(responsavelProjeto.getNome());
        dto.setEmailResponsavelProjeto(responsavelProjeto.getEmail());
        dto.setComentario(tarefa.getComentario());
        dto.setComImpedimento(tarefa.getComImpedimento());
        return dto;
    }
}
