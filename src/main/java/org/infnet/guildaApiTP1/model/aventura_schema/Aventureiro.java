package org.infnet.guildaApiTP1.model.aventura_schema;

import jakarta.persistence.*;
import lombok.*;
import org.infnet.guildaApiTP1.enums.ClasseEnum;
import org.infnet.guildaApiTP1.model.audit_schema.Organizacao;
import org.infnet.guildaApiTP1.model.audit_schema.Usuario;


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

    @Column(name = "nome", length = 120, nullable = false)
    private String nome;

    @Column(name = "classe", nullable = false)
    @Enumerated(EnumType.STRING)
    private ClasseEnum classe;


}
