package org.infnet.guildaApiTP1.dto;

import org.infnet.guildaApiTP1.enums.ClasseEnum;

public record AventureiroCompletoDTO(
        Long id,
        String nome,
        ClasseEnum classe,
        Integer nivel,
        Boolean ativo,
        OrganizacaoDTO organizacao,
        CompanheiroDTO companheiro,
        Integer qtdTotalMissoes,
        UltimaMissaoDTO ultimaMissao) {
}
