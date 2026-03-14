package org.infnet.guildaApiTP1.model.audit_schema;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.infnet.guildaApiTP1.enums.StatusUsuarioEnum;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(schema="audit", name="usuarios", uniqueConstraints = {
        @UniqueConstraint(
                name = "uq_usuarios_email_por_org",
                columnNames = {"organizacao_id", "email"})
})
public class Usuario {
    @Id@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional=false,fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @OneToMany(mappedBy = "usuario")
    private Set<UserRole> roles = new HashSet<>();

    @Column(name = "nome", length = 120, nullable = false)
    private String nome;

    @Column(name = "email", length = 180, nullable = false)
    private String email;

    @Column(name = "senha_hash", nullable = false)
    private String senhaHash;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",length = 30, nullable = false)
    private StatusUsuarioEnum status;

    @Column(name = "ultimo_login_em")
    private ZonedDateTime ultimoLoginEm;

    @Column(name = "created_at", nullable = false, updatable = false, insertable = false)
    private ZonedDateTime createdAt;

    @Column(name = "updated_at", nullable = false, updatable = false, insertable = false)
    private ZonedDateTime updatedAt;
}
