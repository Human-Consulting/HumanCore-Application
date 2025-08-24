package com.humanconsulting.humancore_api.novo.infrastructure.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Area {
    private String nome;
    private Integer valor; // ou Integer, dependendo da contagem
}
