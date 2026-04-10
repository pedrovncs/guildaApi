package org.infnet.guildaApiTP1.operacoes.dto;

import org.infnet.guildaApiTP1.aventura.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.aventura.enums.StatusMissaoEnum;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PainelTaticoMissaoDTO(
        Long id,
        String titulo,
        StatusMissaoEnum status,
        NivelPerigoEnum nivelPerigo,
        Long organizacaoId,
        Long totalParticipantes,
        BigDecimal nivelMedioEquipe,
        BigDecimal totalRecompensa,
        Long totalMvps,
        Long participantesComCompanheiros,
        LocalDateTime ultimaAtualizacao,
        BigDecimal indiceProntidao) implements Serializable {
}