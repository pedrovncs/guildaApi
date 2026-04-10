package org.infnet.guildaApiTP1.audit.dto;

import org.infnet.guildaApiTP1.audit.enums.StatusUsuarioEnum;

import java.util.List;

public record UsuarioComRolesDTO(
        Long id,
        String nome,
        String email,
        StatusUsuarioEnum status,
        OrganizacaoDTO organizacao,
        List<RoleDTO> roles
) {
}
