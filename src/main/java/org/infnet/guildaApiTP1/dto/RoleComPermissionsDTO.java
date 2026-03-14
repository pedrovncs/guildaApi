package org.infnet.guildaApiTP1.dto;

import java.security.Permissions;
import java.util.List;

public record RoleComPermissionsDTO(
        Long id,
        String nome,
        String descricao,
        List<PermissionDTO> permissions
) {
}
