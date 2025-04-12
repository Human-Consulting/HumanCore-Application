package com.humanconsulting.humancore_api.mapper;

import com.humanconsulting.humancore_api.controller.dto.request.EntregaRequestDto;
import com.humanconsulting.humancore_api.controller.dto.response.EntregaResponseDto;
import com.humanconsulting.humancore_api.model.Entrega;

public class EntregaMapper {
    public static Entrega toEntity(EntregaRequestDto entregaRequestDto) {
        return new Entrega(entregaRequestDto.getDescricao(), entregaRequestDto.getDtInicio(), entregaRequestDto.getDtFim(), entregaRequestDto.getFkSprint(), entregaRequestDto.getFkResponsavel());
    }

    public static EntregaResponseDto toDto(Entrega entrega, String nomeResponsavel, String areaResponsavel) {
        return new EntregaResponseDto(entrega.getIdEntrega(), entrega.getDescricao(), entrega.getDtInicio(), entrega.getDtFim(), entrega.getProgresso(), entrega.getComImpedimento(), entrega.getFkSprint(), entrega.getFkResponsavel(), nomeResponsavel, areaResponsavel);
    }
}
