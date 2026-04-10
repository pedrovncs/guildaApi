package org.infnet.guildaApiTP1.aventura.repository;

import org.infnet.guildaApiTP1.aventura.model.Companheiro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanheiroRepository extends JpaRepository<Companheiro, Long> {
}
