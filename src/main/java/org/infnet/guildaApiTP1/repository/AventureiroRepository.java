package org.infnet.guildaApiTP1.repository;

import org.infnet.guildaApiTP1.dto.AventureiroDTO;
import org.infnet.guildaApiTP1.enums.ClasseEnum;
import org.infnet.guildaApiTP1.model.aventura_schema.Aventureiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AventureiroRepository extends JpaRepository<Aventureiro, Long> {

    @Query("""
        select new org.infnet.guildaApiTP1.dto.AventureiroDTO(
            a.id,
            a.nome,
            a.classe,
            a.nivel,
            a.ativo
        )
        from Aventureiro a
        where (:ativo is null or a.ativo = :ativo)
            and (:classe is null or a.classe = :classe)
            and (:nivelMinimo is null or a.nivel >= :nivelMinimo)
    """)
    Page<AventureiroDTO> buscarComFiltros(
            @Param("ativo") Boolean ativo,
            @Param("classe")ClasseEnum classe,
            @Param("nivelMinimo") Integer nivelMinimo,
            Pageable pageable);

    Page<Aventureiro> findByNomeContainingIgnoreCase(String nome, Pageable pageable);

    @EntityGraph(attributePaths = {"companheiro", "organizacao"})
    Optional<Aventureiro> findAventureiroComCompanheiroEOrganizacaoById(Long id);
}
