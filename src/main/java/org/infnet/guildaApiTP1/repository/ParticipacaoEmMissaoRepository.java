package org.infnet.guildaApiTP1.repository;

import org.infnet.guildaApiTP1.model.aventura_schema.ParticipacaoEmMissao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipacaoEmMissaoRepository extends JpaRepository<ParticipacaoEmMissao, Long> {

    Boolean existsByMissaoIdAndAventureiroId(Long missaoId, Long aventureiroId);
}
