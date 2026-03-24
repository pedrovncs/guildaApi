package org.infnet.guildaApiTP1.model.audit_schema;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@Table(schema = "audit", name = "api_keys",
        uniqueConstraints = @UniqueConstraint(
                name = "uq_api_keys_nome_por_org",
                columnNames = {"organizacao_id", "nome"})
)
public class ApiKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id")
    private Organizacao organizacao;

    @Column(name = "nome", length = 120, nullable = false)
    private String nome;

    @Column(name = "key_hash", nullable = false)
    private String keyHash;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo;

    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;

    @Column(name = "last_used_at")
    private ZonedDateTime lastUsedAt;
}
