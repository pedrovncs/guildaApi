package org.infnet.guildaApiTP1.repository;

import org.infnet.guildaApiTP1.model.audit_schema.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @EntityGraph(attributePaths = {"permissions"})
    Role findWithPermissionsById(Long id);
}
