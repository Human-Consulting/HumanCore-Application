package com.humanconsulting.humancore_api.web.dtos.response.email;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailUpdateResponseDto {
    private String descricaoProjeto;
    private String descricaoSprint;
    private String descricaoTarefa;
    private String nomeResponsavelTarefa;
    private String emailResponsavelTarefa;
    private String nomeResponsavelProjeto;
    private String emailResponsavelProjeto;
    private String comentario;
    private boolean comImpedimento;
}
