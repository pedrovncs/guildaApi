package org.infnet.guildaApiTP1.audit.repository;

import org.infnet.guildaApiTP1.audit.model.Organizacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrganizacaoRepository extends JpaRepository<Organizacao, Long> {
}
