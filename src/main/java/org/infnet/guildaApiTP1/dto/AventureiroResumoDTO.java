package org.infnet.guildaApiTP1.dto;

import org.infnet.guildaApiTP1.enums.ClasseEnum;
import org.infnet.guildaApiTP1.enums.EspecieEnum;

public record AventureiroResumoDTO(
        Long id,
        String nome,
        ClasseEnum classe,
        Integer nivel,
        boolean ativo
) {

}
