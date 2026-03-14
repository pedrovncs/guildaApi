package org.infnet.guildaApiTP1.model.audit_schema;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter@Setter
@Table(schema="audit", name="permissions")
public class Permission {
    @Id@GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(mappedBy = "permissions")
    private Set<Role> roles = new HashSet<>();

    @Column(name = "code", unique = true, length = 80, nullable = false)
    private String code;

    @Column(name = "descricao", nullable = false)
    private String descricao;
}
