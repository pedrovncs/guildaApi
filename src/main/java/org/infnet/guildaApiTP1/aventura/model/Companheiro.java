package org.infnet.guildaApiTP1.aventura.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;
import org.infnet.guildaApiTP1.aventura.enums.EspecieEnum;

@Entity
@Setter
@Getter
@NoArgsConstructor
@Table(schema = "aventura", name = "companheiros")
public class Companheiro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "aventureiro_id", nullable = false, unique = true)
    private Aventureiro aventureiro;

    @Column(name = "nome", nullable = false, length = 120)
    private String nome;

    @Column(name = "especie", nullable = false)
    @Enumerated(EnumType.STRING)
    private EspecieEnum especie;

    @Min(0)
    @Max(100)
    @Column(name = "indice_lealdade", nullable = false)
    private Integer indiceLealdade;
}
