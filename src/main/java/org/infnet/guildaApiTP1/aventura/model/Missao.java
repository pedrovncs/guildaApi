package org.infnet.guildaApiTP1.aventura.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.infnet.guildaApiTP1.aventura.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.aventura.enums.StatusMissaoEnum;
import org.infnet.guildaApiTP1.audit.model.Organizacao;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(schema = "aventura", name = "missoes")
public class Missao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @Column(name = "titulo", nullable = false, length = 150)
    private String titulo;

    @Column(name = "nivel_de_perigo", nullable = false)
    @Enumerated(EnumType.STRING)
    private NivelPerigoEnum nivelDePerigo;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusMissaoEnum status;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false,
            columnDefinition = "timestamptz default now()")
    private ZonedDateTime createdAt;

    @Column(name = "data_inicio")
    private ZonedDateTime dataInicio;

    @Column(name = "data_termino")
    private ZonedDateTime dataTermino;

    @OneToMany(mappedBy = "missao", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParticipacaoEmMissao> participacoes = new ArrayList<>();

    public Boolean aceitaNovosParticipantes() {
        return this.status == StatusMissaoEnum.PLANEJADA;
    }
}
