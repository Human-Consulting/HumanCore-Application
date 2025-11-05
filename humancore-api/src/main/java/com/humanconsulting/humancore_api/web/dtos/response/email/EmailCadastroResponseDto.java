package com.humanconsulting.humancore_api.web.dtos.response.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailCadastroResponseDto {
    public String nome;
    public String email;
    public String senha;
    public String cargo;
    public String area;
    public String nomeEmpresa;
}
