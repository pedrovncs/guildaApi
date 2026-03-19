package org.infnet.guildaApiTP1.dto;

import org.infnet.guildaApiTP1.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.enums.PapelNaMissaoEnum;
import org.infnet.guildaApiTP1.enums.StatusMissaoEnum;

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
