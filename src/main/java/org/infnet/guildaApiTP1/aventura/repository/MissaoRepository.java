package org.infnet.guildaApiTP1.aventura.repository;

import org.infnet.guildaApiTP1.aventura.dto.MissaoDTO;
import org.infnet.guildaApiTP1.aventura.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.aventura.enums.StatusMissaoEnum;
import org.infnet.guildaApiTP1.aventura.model.Missao;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

@Repository
public interface MissaoRepository extends JpaRepository<Missao, Long> {

    @EntityGraph(attributePaths = {"organizacao"})
    Optional<Missao> findComOrganizacaoById(long id);

    @Query("""
               select new org.infnet.guildaApiTP1.aventura.dto.MissaoDTO(
                    m.id,
                    m.titulo,
                    m.nivelDePerigo,
                    m.status,
                    m.dataInicio,
                    m.dataTermino
                    )
                    from Missao m
                    where (:status is null or m.status = :status)
                    and (:nivelDePerigo is null or m.nivelDePerigo = :nivelDePerigo)
                    and m.dataInicio >= :dataIntervaloInicio
                    and m.dataInicio <= :dataIntervaloFim
            """)
    Page<MissaoDTO> buscarComFiltros(
            @Param("status") StatusMissaoEnum status,
            @Param("nivelDePerigo") NivelPerigoEnum nivelDePerigo,
            @Param("dataIntervaloInicio") ZonedDateTime dataInicio,
            @Param("dataIntervaloFim") ZonedDateTime dataFim,
            Pageable pageable);
}
