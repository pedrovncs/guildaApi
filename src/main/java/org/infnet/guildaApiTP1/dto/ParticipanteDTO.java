package org.infnet.guildaApiTP1.dto;

import org.infnet.guildaApiTP1.enums.PapelNaMissaoEnum;

public record ParticipanteDTO(
        Long id,
        String nome,
        PapelNaMissaoEnum papelNaMissao,
        Integer recompensaEmOuro,
        Boolean destaqueMvp
) {
}
