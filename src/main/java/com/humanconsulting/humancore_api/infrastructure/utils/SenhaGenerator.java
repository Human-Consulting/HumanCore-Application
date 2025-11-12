package com.humanconsulting.humancore_api.infrastructure.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class SenhaGenerator {

    public static String execute(String nome, String empresa) {
        Random random = new Random();

        char inicialNome = Character.toUpperCase(nome.charAt(0));

        int numeroAleatorio = 100 + random.nextInt(900);

        String empresaFormatada = empresa.replaceAll("\\s+", "").toUpperCase();
        String letrasEmpresa = empresaFormatada.substring(0, Math.min(3, empresaFormatada.length()));

        LocalTime agora = LocalTime.now();
        String horaMinuto = agora.format(DateTimeFormatter.ofPattern("HHmm"));

        char[] simbolos = {'@', '#', '$', '%', '&', '*'};
        char simbolo = simbolos[random.nextInt(simbolos.length)];

        return String.format("%c%c%d%s%s", '@', inicialNome, numeroAleatorio, letrasEmpresa, horaMinuto) + simbolo;
    }
}
