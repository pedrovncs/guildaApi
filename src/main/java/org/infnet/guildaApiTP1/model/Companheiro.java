package org.infnet.guildaApiTP1.model;

import lombok.*;
import org.infnet.guildaApiTP1.enums.EspecieEnum;

@Setter @Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Companheiro {
    private String nome;
    private EspecieEnum especie;
    private Integer lealdade;
}
