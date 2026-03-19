package org.infnet.guildaApiTP1.service;

import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.dto.*;
import org.infnet.guildaApiTP1.enums.ClasseEnum;
import org.infnet.guildaApiTP1.exceptions.EntityNotFoundException;
import org.infnet.guildaApiTP1.model.aventura_schema.Aventureiro;
import org.infnet.guildaApiTP1.repository.AventureiroRepository;
import org.infnet.guildaApiTP1.repository.ParticipacaoEmMissaoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AventureiroService {
    private final AventureiroRepository aventureiroRepository;
    private final ParticipacaoEmMissaoRepository participacaoEmMissaoRepository;

    public Page<AventureiroDTO> listarAventureirosComFiltros(Boolean ativo, ClasseEnum classe, Integer nivelMinimo, Pageable pageable) {
        return aventureiroRepository.buscarComFiltros(ativo, classe, nivelMinimo, pageable);
    }

    public Page<AventureiroDTO> listarAventureirosPorNome(String nome, Pageable pageable) {
        return aventureiroRepository.findByNomeContainingIgnoreCase(nome, pageable)
                .map(a -> new AventureiroDTO(
                        a.getId(),
                        a.getNome(),
                        a.getClasse(),
                        a.getNivel(),
                        a.isAtivo()
                ));
    }

    public AventureiroCompletoDTO buscarAventureiroCompleto(Long id){
        Aventureiro aventureiro = aventureiroRepository.findAventureiroComCompanheiroEOrganizacaoById(id)
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));

        OrganizacaoDTO organizacao = new OrganizacaoDTO(
                aventureiro.getOrganizacao().getId(),
                aventureiro.getOrganizacao().getNome()
        );

        CompanheiroDTO companheiro = aventureiro.getCompanheiro() != null
                ? new CompanheiroDTO(
                        aventureiro.getCompanheiro().getNome(),
                        aventureiro.getCompanheiro().getEspecie(),
                        aventureiro.getCompanheiro().getIndiceLealdade()
                )
                : null;

        UltimaMissaoDTO ultimaMissao = participacaoEmMissaoRepository
                .buscarUltimaMissao(aventureiro.getId(), PageRequest.of(0,1))
                .stream().findFirst()
                .orElse(null);

        Integer qtdTotalMissoes = participacaoEmMissaoRepository.countByAventureiroId(aventureiro.getId());

        return new AventureiroCompletoDTO(
                aventureiro.getId(),
                aventureiro.getNome(),
                aventureiro.getClasse(),
                aventureiro.getNivel(),
                aventureiro.isAtivo(),
                organizacao,
                companheiro,
                qtdTotalMissoes,
                ultimaMissao
        );
    }
}
