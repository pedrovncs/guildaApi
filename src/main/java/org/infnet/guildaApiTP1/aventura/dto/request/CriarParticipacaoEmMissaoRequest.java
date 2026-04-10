package org.infnet.guildaApiTP1.aventura.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.infnet.guildaApiTP1.aventura.enums.PapelNaMissaoEnum;

public record CriarParticipacaoEmMissaoRequest(
        @NotNull(message = "O ID da missão é obrigatório")
        Long missaoId,

        @NotNull(message = "O ID do aventureiro é obrigatório")
        Long aventureiroId,

        @NotNull(message = "O papel na missão é obrigatório")
        PapelNaMissaoEnum papelNaMissao,

        @Min(0)
        Integer recompensaEmOuro,

        @NotNull
        Boolean destaqueMvp
) {
}
