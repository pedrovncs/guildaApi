package org.infnet.guildaApiTP1.model.audit_schema;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Entity
@Getter
@Setter
@Table(schema = "audit", name = "roles", uniqueConstraints = {
        @UniqueConstraint(
                name = "uq_roles_nome_por_org",
                columnNames = {"organizacao_id", "nome"})
})
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @ManyToMany
    @JoinTable(
            schema = "audit",
            name = "role_permissions",
            joinColumns = @JoinColumn(
                    nullable = false,
                    name = "role_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    nullable = false,
                    name = "permission_id"
            )
    )
    private Set<Permission> permissions = new HashSet<>();

    @OneToMany(mappedBy = "role")
    private Set<UserRole> roles = new HashSet<>();

    @Column(name = "nome", length = 60, nullable = false)
    private String nome;

    @Column(name = "descricao")
    private String descricao;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private Date createdAt;
}
