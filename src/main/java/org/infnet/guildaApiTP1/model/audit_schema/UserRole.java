package org.infnet.guildaApiTP1.model.audit_schema;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.infnet.guildaApiTP1.model.audit_schema.keys.UserRoleId;

import java.time.ZonedDateTime;

@Entity
@Table(schema ="audit", name="user_roles")
@Getter@Setter
public class UserRole {
    @EmbeddedId
    private UserRoleId userRoleId;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("usuarioId")
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "granted_at", nullable = false)
    private ZonedDateTime grantedAt;
}
