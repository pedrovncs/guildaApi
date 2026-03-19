package org.infnet.guildaApiTP1.dto;

import org.infnet.guildaApiTP1.enums.EspecieEnum;

public record CompanheiroDTO(
        String nome,
        EspecieEnum especie,
        Integer lealdade) {
}
