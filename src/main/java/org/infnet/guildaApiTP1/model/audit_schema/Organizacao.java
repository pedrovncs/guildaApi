package org.infnet.guildaApiTP1.model.audit_schema;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;


@Entity
@Getter@Setter
@Table(schema="audit", name="organizacoes")
public class Organizacao {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "organizacao")
    private List<Usuario> usuarios;

    @Column(name="nome", nullable = false, unique = true, length = 120)
    private String nome;

    @Column(name="ativo", nullable = false)
    private Boolean ativo;

    @Column(name="created_at", nullable = false, updatable = false, insertable = false)
    private ZonedDateTime createdAt;
}
