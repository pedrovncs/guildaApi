package org.infnet.guildaApiTP1.repository;

import org.infnet.guildaApiTP1.enums.ClasseEnum;
import org.infnet.guildaApiTP1.model.aventura_schema.Aventureiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

    @Query("""
        select a
        from Aventureiro a
        where (:ativo is null or a.ativo = :ativo)
            and (:classe is null or a.classe = :classe)
            and (:nivelMinimo is null or a.nivel >= :nivelMinimo)
    """)
    Page<Aventureiro> buscarComFiltros(@Param("ativo") Boolean ativo,
                                       @Param("classe")ClasseEnum classe,
                                       @Param(" nivelMinimo") Integer nivelMinimo,
                                       Pageable pageable);
}
