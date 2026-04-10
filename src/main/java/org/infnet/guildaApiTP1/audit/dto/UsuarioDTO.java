package org.infnet.guildaApiTP1.audit.dto;

import org.infnet.guildaApiTP1.audit.enums.StatusUsuarioEnum;

public record UsuarioDTO(
        Long id,
        String nome,
        String email,
        StatusUsuarioEnum status,
        OrganizacaoDTO organizacao
) {
}
