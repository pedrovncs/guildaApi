package org.infnet.guildaApiTP1.dto;

import org.infnet.guildaApiTP1.enums.StatusUsuarioEnum;
import org.infnet.guildaApiTP1.model.audit_schema.Organizacao;

import java.time.ZonedDateTime;

public record UsuarioDTO(
        Long id,
        String nome,
        String email,
        StatusUsuarioEnum status,
        OrganizacaoDTO organizacao
) {
}
