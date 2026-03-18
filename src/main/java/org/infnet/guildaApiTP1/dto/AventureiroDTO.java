package org.infnet.guildaApiTP1.dto;

import org.infnet.guildaApiTP1.enums.ClasseEnum;

public record AventureiroDTO(
        Long id,
        String nome,
        ClasseEnum classe,
        Integer nivel,
        boolean ativo
) {

}
