package org.infnet.guildaApiTP1.model.audit_schema;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.net.InetAddress;
import java.time.ZonedDateTime;
import java.util.Map;

@Entity
@Getter
@Setter
@Table(schema = "audit", name = "audit_entries")
public class AuditEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "organizacao_id", nullable = false)
    private Organizacao organizacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_user_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_api_key_id")
    private ApiKey apiKey;

    @Column(name = "action", length = 30, nullable = false)
    private String action;

    @Column(name = "entity_schema", length = 60, nullable = false)
    private String entitySchema;

    @Column(name = "entity_name", length = 80, nullable = false)
    private String entityName;

    @Column(name = "entity_id", length = 80)
    private String entityId;

    @Column(name = "occurred_at", nullable = false)
    private ZonedDateTime occuredAt;

    @Column(name = "ip")
    private InetAddress ip;

    @Column(name = "user_agent")
    private String userAgent;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "diff", columnDefinition = "jsonb")
    private Map<String, Object> diff;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "jsonb")
    private Map<String, Object> metadata;

    @Column(name = "success", nullable = false)
    private Boolean success;

}
