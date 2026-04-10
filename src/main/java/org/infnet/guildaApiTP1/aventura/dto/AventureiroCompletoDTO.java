package org.infnet.guildaApiTP1.aventura.dto;

import org.infnet.guildaApiTP1.audit.dto.OrganizacaoDTO;
import org.infnet.guildaApiTP1.aventura.enums.ClasseEnum;

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
