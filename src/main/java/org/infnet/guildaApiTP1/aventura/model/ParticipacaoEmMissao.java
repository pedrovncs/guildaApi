package org.infnet.guildaApiTP1.aventura.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.infnet.guildaApiTP1.aventura.enums.PapelNaMissaoEnum;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "aventura", name = "participacao_em_missoes", uniqueConstraints = {
        @UniqueConstraint(
                name = "uq_participacao_aventureiro_missao",
                columnNames = {"aventureiro_id", "missao_id"})
})

public class ParticipacaoEmMissao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "missao_id", nullable = false)
    private Missao missao;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "aventureiro_id", nullable = false)
    private Aventureiro aventureiro;

    @Column(name = "papel_na_missao", nullable = false)
    @Enumerated(EnumType.STRING)
    private PapelNaMissaoEnum papelNaMissao;

    @Min(0)
    @Column(name = "recompensa_em_ouro")
    private Integer recompensaEmOuro;

    @Column(name = "destaque_mvp", nullable = false)
    private Boolean destaqueMvp;

    @Column(name = "data_registro", nullable = false, updatable = false, insertable = false,
            columnDefinition = "timestamptz default now()")
    private ZonedDateTime dataRegistro;
}
