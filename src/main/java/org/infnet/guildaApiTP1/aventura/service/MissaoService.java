package org.infnet.guildaApiTP1.aventura.service;

import org.infnet.guildaApiTP1.audit.dto.OrganizacaoDTO;
import org.infnet.guildaApiTP1.aventura.dto.MissaoCompletaDTO;
import org.infnet.guildaApiTP1.aventura.dto.MissaoDTO;
import org.infnet.guildaApiTP1.aventura.dto.ParticipanteDTO;
import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.aventura.enums.NivelPerigoEnum;
import org.infnet.guildaApiTP1.aventura.enums.StatusMissaoEnum;
import org.infnet.guildaApiTP1.common.exceptions.EntityNotFoundException;
import org.infnet.guildaApiTP1.aventura.model.Missao;
import org.infnet.guildaApiTP1.aventura.repository.MissaoRepository;
import org.infnet.guildaApiTP1.aventura.repository.ParticipacaoEmMissaoRepository;
import org.infnet.guildaApiTP1.common.util.ValidarDataIntervaloUtils;
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
