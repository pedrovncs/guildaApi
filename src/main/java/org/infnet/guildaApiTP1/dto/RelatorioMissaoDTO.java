package org.infnet.guildaApiTP1.dto;

import org.infnet.guildaApiTP1.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.enums.StatusMissaoEnum;

public record RelatorioMissaoDTO(
        String titulo,
        StatusMissaoEnum status,
        NivelPerigoEnum nivelDePerigo,
        Long totalParticipantes,
        Long totalRecompensas
) {

}
