package org.infnet.guildaApiTP1.aventura.dto;

import org.infnet.guildaApiTP1.aventura.enums.PapelNaMissaoEnum;

public record ParticipanteDTO(
        Long id,
        String nome,
        PapelNaMissaoEnum papelNaMissao,
        Integer recompensaEmOuro,
        Boolean destaqueMvp
) {
}
