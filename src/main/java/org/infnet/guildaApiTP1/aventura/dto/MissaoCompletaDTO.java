package org.infnet.guildaApiTP1.aventura.dto;

import org.infnet.guildaApiTP1.audit.dto.OrganizacaoDTO;
import org.infnet.guildaApiTP1.aventura.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.aventura.enums.StatusMissaoEnum;

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
