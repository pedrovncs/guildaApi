package org.infnet.guildaApiTP1.aventura.dto;

import org.infnet.guildaApiTP1.aventura.enums.ClasseEnum;

public record AventureiroDTO(
        Long id,
        String nome,
        ClasseEnum classe,
        Integer nivel,
        boolean ativo
) {

}
