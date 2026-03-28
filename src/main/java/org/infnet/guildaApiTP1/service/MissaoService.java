package org.infnet.guildaApiTP1.service;

import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.dto.*;
import org.infnet.guildaApiTP1.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.enums.StatusMissaoEnum;
import org.infnet.guildaApiTP1.exceptions.EntityNotFoundException;
import org.infnet.guildaApiTP1.model.aventura_schema.Missao;
import org.infnet.guildaApiTP1.repository.MissaoRepository;
import org.infnet.guildaApiTP1.repository.ParticipacaoEmMissaoRepository;
import org.infnet.guildaApiTP1.util.ValidarDataIntervaloUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.time.ZonedDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MissaoService {
    private final ParticipacaoEmMissaoRepository participacaoRepository;
    private final MissaoRepository missaoRepository;

    public Page<MissaoDTO> buscarMissaoComFiltros(
            StatusMissaoEnum status,
            NivelPerigoEnum nivelDePerigo,
            ZonedDateTime dataIntervaloInicio,
            ZonedDateTime dataIntervaloFim,
            Pageable pageable
    ) {

        ZonedDateTime dataInicio = ValidarDataIntervaloUtils.intervaloInicioOuPadrao(dataIntervaloInicio);
        ZonedDateTime dataFim = ValidarDataIntervaloUtils.intervaloFimOuPadrao(dataIntervaloFim);

        return missaoRepository.buscarComFiltros(
                status,
                nivelDePerigo,
                dataInicio,
                dataFim,
                pageable);
    }

    public MissaoCompletaDTO buscarMissaoCompleta(Long missaoId) {
        Missao missao = missaoRepository.findComOrganizacaoById(missaoId)
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada"));

        OrganizacaoDTO organizacao = new OrganizacaoDTO(
                missao.getOrganizacao().getId(),
                missao.getOrganizacao().getNome()
        );

        List<ParticipanteDTO> participantes = participacaoRepository.buscarParticipantesPorMissaoId(missaoId);

        return new MissaoCompletaDTO(
                missao.getId(),
                missao.getTitulo(),
                organizacao,
                missao.getNivelDePerigo(),
                missao.getStatus(),
                missao.getCreatedAt(),
                missao.getDataInicio(),
                missao.getDataTermino(),
                participantes
        );

    }
}
