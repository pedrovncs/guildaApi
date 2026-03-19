package org.infnet.guildaApiTP1.repository;

import org.infnet.guildaApiTP1.model.audit_schema.Usuario;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @EntityGraph(attributePaths = {"roles", "roles.role", "organizacao"})
    Optional<Usuario> findWithRolesById(Long id);
}