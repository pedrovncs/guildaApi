package org.infnet.guildaApiTP1.repository;

import org.infnet.guildaApiTP1.model.aventura_schema.Missao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, Long> {
}
