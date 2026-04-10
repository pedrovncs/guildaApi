package org.infnet.guildaApiTP1.audit.model;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
public class UserRoleId implements Serializable {
    private Long usuarioId;
    private Long roleId;
}