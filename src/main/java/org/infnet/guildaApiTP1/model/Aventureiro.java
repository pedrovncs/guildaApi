package org.infnet.guildaApiTP1.model;

import lombok.*;
import org.infnet.guildaApiTP1.enums.ClasseEnum;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@Setter@Getter
@ToString
@NoArgsConstructor
public class Aventureiro {
    private static final AtomicLong counter = new AtomicLong(1);

    private Long id;
    private String nome;
    private ClasseEnum classe;
    private Integer nivel;
    private boolean ativo;

    private Companheiro companheiro;

    public Aventureiro(String nome, ClasseEnum classe, int nivel) {
        this.id = counter.getAndIncrement();
        this.nome = nome;
        this.classe = classe;
        this.nivel = nivel;
        this.ativo = true;
        this.companheiro = null;
    }
}
