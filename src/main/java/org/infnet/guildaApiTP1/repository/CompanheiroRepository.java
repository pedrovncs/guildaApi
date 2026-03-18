package org.infnet.guildaApiTP1.repository;

import org.infnet.guildaApiTP1.model.aventura_schema.Companheiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanheiroRepository extends JpaRepository<Companheiro, Long> {
}
