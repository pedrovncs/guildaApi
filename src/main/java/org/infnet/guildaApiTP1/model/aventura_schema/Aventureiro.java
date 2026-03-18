package org.infnet.guildaApiTP1.model.aventura_schema;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.infnet.guildaApiTP1.enums.ClasseEnum;
import org.infnet.guildaApiTP1.model.audit_schema.Organizacao;
import org.infnet.guildaApiTP1.model.audit_schema.Usuario;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(schema = "aventura", name = "aventureiros")
@Setter@Getter
@NoArgsConstructor
public class Aventureiro {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_responsavel_id", nullable = false)
    private Usuario usuarioResponsavel;

    @OneToOne(mappedBy = "aventureiro", cascade = CascadeType.ALL, orphanRemoval = true)
    private Companheiro companheiro;

    @Column(name = "nome", length = 120, nullable = false)
    private String nome;

    @Column(name = "classe", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClasseEnum classe;

    @Min(1)
    @Column(name = "nivel", nullable = false)
    private Integer nivel;

    @Column(name= "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false,
    columnDefinition = "timestamptz default now()")
    private ZonedDateTime createdAt;

    @Column(name = "updated_at", nullable = false, insertable = false,
    columnDefinition = "timestamptz default now()")
    private ZonedDateTime updatedAt;

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = ZonedDateTime.now();
    }

    @OneToMany(mappedBy = "aventureiro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ParticipacaoEmMissao> participacoes = new ArrayList<>();

    public void setCompanheiro(Companheiro companheiro) {
        this.companheiro = companheiro;

        if (companheiro != null) {
            companheiro.setAventureiro(this);
        }
    }

    public Boolean pertenceAOrganizacao(Long organizacaoId) {
        return this.organizacao != null && this.organizacao.getId().equals(organizacaoId);
    }

    public Boolean isAtivo() {
        return this.ativo != null && this.ativo;
    }
}
