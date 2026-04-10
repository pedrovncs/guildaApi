package org.infnet.guildaApiTP1.audit.repository;

import org.infnet.guildaApiTP1.audit.model.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @EntityGraph(attributePaths = {"permissions"})
    Optional<Role> findWithPermissionsById(Long id);
}
