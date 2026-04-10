package org.infnet.guildaApiTP1.aventura.dto;

import org.infnet.guildaApiTP1.aventura.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.aventura.enums.PapelNaMissaoEnum;
import org.infnet.guildaApiTP1.aventura.enums.StatusMissaoEnum;

import java.time.ZonedDateTime;

public record UltimaMissaoDTO(
        Long id,
        String titulo,
        NivelPerigoEnum nivelPerigo,
        StatusMissaoEnum status,
        PapelNaMissaoEnum papelNaMissao,
        Integer recompensaEmOuro,
        Boolean destaqueMvp,
        ZonedDateTime dataRegistro) {
}
