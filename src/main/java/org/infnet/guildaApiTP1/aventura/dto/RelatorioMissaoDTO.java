package org.infnet.guildaApiTP1.aventura.dto;

import org.infnet.guildaApiTP1.aventura.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.aventura.enums.StatusMissaoEnum;

public record RelatorioMissaoDTO(
        String titulo,
        StatusMissaoEnum status,
        NivelPerigoEnum nivelDePerigo,
        Long totalParticipantes,
        Long totalRecompensas
) {

}
