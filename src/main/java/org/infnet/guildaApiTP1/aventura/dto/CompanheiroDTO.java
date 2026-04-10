package org.infnet.guildaApiTP1.aventura.dto;

import org.infnet.guildaApiTP1.aventura.enums.EspecieEnum;

public record CompanheiroDTO(
        String nome,
        EspecieEnum especie,
        Integer lealdade) {
}
