package org.infnet.guildaApiTP1.repository;

import org.infnet.guildaApiTP1.model.audit_schema.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {
}
