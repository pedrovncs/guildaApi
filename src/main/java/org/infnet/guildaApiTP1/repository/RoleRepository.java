package org.infnet.guildaApiTP1.repository;

import org.infnet.guildaApiTP1.dto.RoleComPermissionsDTO;
import org.infnet.guildaApiTP1.model.audit_schema.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {

    @EntityGraph(attributePaths = {"permissions"})
    Role findWithPermissionsById(Long id);
}
