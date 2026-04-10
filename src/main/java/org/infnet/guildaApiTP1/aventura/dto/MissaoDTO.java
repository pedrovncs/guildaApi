package org.infnet.guildaApiTP1.aventura.dto;

import org.infnet.guildaApiTP1.aventura.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.aventura.enums.StatusMissaoEnum;

import java.time.ZonedDateTime;

public record MissaoDTO(
        Long id,
        String titulo,
        NivelPerigoEnum nivelPerigo,
        StatusMissaoEnum status,
        ZonedDateTime dataInicio,
        ZonedDateTime dataTermino
) {
}
