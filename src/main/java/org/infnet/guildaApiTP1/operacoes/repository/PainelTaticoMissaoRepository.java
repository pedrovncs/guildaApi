package org.infnet.guildaApiTP1.operacoes.repository;

import org.infnet.guildaApiTP1.operacoes.dto.PainelTaticoMissaoDTO;
import org.infnet.guildaApiTP1.operacoes.model.PainelTaticoMissao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface PainelTaticoMissaoRepository extends JpaRepository<PainelTaticoMissao, Long> {

    @Query("""
        select new org.infnet.guildaApiTP1.operacoes.dto.PainelTaticoMissaoDTO(
            p.id,
            p.titulo,
            p.status,
            p.nivelPerigo,
            p.organizacaoId,
            p.totalParticipantes,
            p.nivelMedioEquipe,
            p.totalRecompensa,
            p.totalMvps,
            p.participantesComCompanheiros,
            p.ultimaAtualizacao,
            p.indiceProntidao
        )
        from PainelTaticoMissao p
        where p.ultimaAtualizacao >= :dataMinima
        order by p.indiceProntidao desc
    """)
    List<PainelTaticoMissaoDTO> buscarTop10DosUltimos15Dias(
            @Param("dataMinima") LocalDateTime dataMinima,
            Pageable pageable);
}
