package org.infnet.guildaApiTP1.aventura.dto;

public record RelatorioParticipacaoDTO(
        Long aventureiroId,
        String nome,
        Integer nivel,
        Long totalDeParticipacoes,
        Long somaRecompensas,
        Long totalDestaquesMvp
) {
}
