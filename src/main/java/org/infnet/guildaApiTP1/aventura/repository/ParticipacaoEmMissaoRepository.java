package org.infnet.guildaApiTP1.aventura.repository;

import org.infnet.guildaApiTP1.aventura.dto.ParticipanteDTO;
import org.infnet.guildaApiTP1.aventura.dto.RelatorioMissaoDTO;
import org.infnet.guildaApiTP1.aventura.dto.RelatorioParticipacaoDTO;
import org.infnet.guildaApiTP1.aventura.dto.UltimaMissaoDTO;
import org.infnet.guildaApiTP1.aventura.enums.StatusMissaoEnum;
import org.infnet.guildaApiTP1.aventura.model.ParticipacaoEmMissao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ParticipacaoEmMissaoRepository extends JpaRepository<ParticipacaoEmMissao, Long> {

    Boolean existsByMissaoIdAndAventureiroId(Long missaoId, Long aventureiroId);

    Integer countByAventureiroId(Long aventureiroId);

    @Query("""
                select new org.infnet.guildaApiTP1.aventura.dto.UltimaMissaoDTO(
                    m.id,
                    m.titulo,
                    m.nivelDePerigo,
                    m.status,
                    p.papelNaMissao,
                    p.recompensaEmOuro,
                    p.destaqueMvp,
                    p.dataRegistro
            
                )
                from ParticipacaoEmMissao p
                join p.missao m
                where p.aventureiro.id = :aventureiroId
                order by p.dataRegistro, p.id desc
            """)
    List<UltimaMissaoDTO> buscarUltimaMissao(@Param("aventureiroId") Long aventureiroId, Pageable pageable);

    @Query("""
                    select new org.infnet.guildaApiTP1.aventura.dto.ParticipanteDTO(
                        a.id,
                        a.nome,
                        p.papelNaMissao,
                        p.recompensaEmOuro,
                        p.destaqueMvp
                    )
                    from ParticipacaoEmMissao p
                    join p.aventureiro a
                    where p.missao.id = :missaoId
            """)
    List<ParticipanteDTO> buscarParticipantesPorMissaoId(@Param("missaoId") Long missaoId);

    @Query("""
                    select new org.infnet.guildaApiTP1.aventura.dto.RelatorioParticipacaoDTO(
                        a.id,
                        a.nome,
                        a.nivel,
                        count(p),
                        sum(p.recompensaEmOuro),
                        sum(case when p.destaqueMvp = true then 1 else 0 end)
                    )
                    from ParticipacaoEmMissao p
                    join p.aventureiro a
                    join p.missao m
                    where (:statusMissao is null or m.status = :statusMissao)
                    and m.dataInicio >= :dataIntervaloInicio
                    and m.dataInicio <= :dataIntervaloFim
                    group by a.id, a.nome, a.nivel
                    order by count(p) desc, sum(p.recompensaEmOuro) desc, sum(case when p.destaqueMvp = true then 1 else 0 end) desc
            """)
    Page<RelatorioParticipacaoDTO> gerarRankingAventureiros(
            @Param("statusMissao") StatusMissaoEnum statusMissao,
            @Param("dataIntervaloInicio") ZonedDateTime dataIntervaloInicio,
            @Param("dataIntervaloFim") ZonedDateTime dataIntervaloFim,
            Pageable pageable
    );

    @Query("""
               select new org.infnet.guildaApiTP1.aventura.dto.RelatorioMissaoDTO(
                        m.titulo,
                        m.status,
                        m.nivelDePerigo,
                        count(p),
                        sum(p.recompensaEmOuro)
                    )
                    from Missao m
                    left join ParticipacaoEmMissao p on p.missao.id = m.id
                    where m.dataInicio >= :dataIntervaloInicio
                    and m.dataInicio <= :dataIntervaloFim
                    group by m.id, m.titulo, m.status, m.nivelDePerigo
            """)
    Page<RelatorioMissaoDTO> gerarRelatorioMissao(
            @Param("dataIntervaloInicio") ZonedDateTime dataIntervaloInicio,
            @Param("dataIntervaloFim") ZonedDateTime dataIntervaloFim,
            Pageable pageable
    );
}
