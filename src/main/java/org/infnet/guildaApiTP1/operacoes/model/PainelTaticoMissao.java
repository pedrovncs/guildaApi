package org.infnet.guildaApiTP1.operacoes.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;
import org.infnet.guildaApiTP1.aventura.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.aventura.enums.StatusMissaoEnum;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vw_painel_tatico_missao", schema = "operacoes")
@Getter@Setter
@Immutable
public class PainelTaticoMissao {
    @Id
    @Column(name="missao_id")
    Long id;

    @Column(name="titulo")
    String titulo;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    StatusMissaoEnum status;

    @Column(name = "nivel_perigo")
    @Enumerated(EnumType.STRING)
    NivelPerigoEnum nivelPerigo;

    @Column(name = "organizacao_id")
    Long organizacaoId;

    @Column(name = "total_participantes")
    Long totalParticipantes;

    @Column(name="nivel_medio_equipe")
    BigDecimal nivelMedioEquipe;

    @Column(name = "total_recompensa")
    BigDecimal totalRecompensa;

    @Column(name="total_mvps")
    Long totalMvps;

    @Column(name = "participantes_com_companheiro")
    Long  participantesComCompanheiros;

    @Column(name="ultima_atualizacao")
    LocalDateTime ultimaAtualizacao;

    @Column(name = "indice_prontidao")
    BigDecimal indiceProntidao;
}
