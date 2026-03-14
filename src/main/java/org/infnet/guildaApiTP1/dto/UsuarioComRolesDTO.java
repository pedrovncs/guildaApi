package org.infnet.guildaApiTP1.dto;

import org.infnet.guildaApiTP1.enums.StatusUsuarioEnum;

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
