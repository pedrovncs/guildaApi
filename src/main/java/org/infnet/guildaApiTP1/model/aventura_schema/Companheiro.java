package org.infnet.guildaApiTP1.model.aventura_schema;

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
