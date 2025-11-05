package com.humanconsulting.humancore_api.infrastructure.mappers;

import com.humanconsulting.humancore_api.domain.entities.Usuario;
import com.humanconsulting.humancore_api.web.dtos.response.email.EmailCadastroResponseDto;

public class EmailCadastroMapper {
    public static EmailCadastroResponseDto toEmailCadastroResponseDto(Usuario usuario) {
        EmailCadastroResponseDto dto = new EmailCadastroResponseDto();
        dto.nome = usuario.getNome();
        dto.email = usuario.getEmail();
        dto.senha = usuario.getSenha();
        dto.cargo = usuario.getCargo();
        dto.area = usuario.getArea();
        dto.nomeEmpresa = usuario.getEmpresa() != null ? usuario.getEmpresa().getNome() : null;
        return dto;
    }
}
