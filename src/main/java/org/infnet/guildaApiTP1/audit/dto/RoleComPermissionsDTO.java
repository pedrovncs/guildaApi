package org.infnet.guildaApiTP1.audit.dto;

import java.util.List;

public record RoleComPermissionsDTO(
        Long id,
        String nome,
        String descricao,
        List<PermissionDTO> permissions
) {
}
