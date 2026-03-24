package org.infnet.guildaApiTP1.service;

import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.dto.PermissionDTO;
import org.infnet.guildaApiTP1.dto.RoleComPermissionsDTO;
import org.infnet.guildaApiTP1.exceptions.EntityNotFoundException;
import org.infnet.guildaApiTP1.model.audit_schema.Role;
import org.infnet.guildaApiTP1.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository repository;

    public RoleComPermissionsDTO buscarPorIdComPermissions(Long roleId) {
        Role role = repository.findWithPermissionsById(roleId)
                .orElseThrow(() -> new EntityNotFoundException("Role não encontrada"));

        List<PermissionDTO> listaPermissions = role.getPermissions().stream()
                .map(p -> new PermissionDTO(
                        p.getCode(),
                        p.getDescricao()
                )).toList();

        return new RoleComPermissionsDTO(
                role.getId(),
                role.getNome(),
                role.getDescricao(),
                listaPermissions
        );
    }
}
