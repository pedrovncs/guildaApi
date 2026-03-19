package org.infnet.guildaApiTP1.dto;

import org.infnet.guildaApiTP1.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.enums.StatusMissaoEnum;

import java.time.ZonedDateTime;
import java.util.List;

public record MissaoCompletaDTO(
        Long id,
        String titulo,
        OrganizacaoDTO organizacao,
        NivelPerigoEnum nivelPerigo,
        StatusMissaoEnum status,
        ZonedDateTime created_at,
        ZonedDateTime dataInicio,
        ZonedDateTime dataTermino,
        List<ParticipanteDTO> participantes
) {
}
