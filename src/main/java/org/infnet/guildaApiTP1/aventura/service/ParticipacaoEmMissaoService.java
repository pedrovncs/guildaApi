package org.infnet.guildaApiTP1.aventura.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.aventura.dto.request.CriarParticipacaoEmMissaoRequest;
import org.infnet.guildaApiTP1.aventura.dto.RelatorioMissaoDTO;
import org.infnet.guildaApiTP1.aventura.dto.RelatorioParticipacaoDTO;
import org.infnet.guildaApiTP1.aventura.enums.StatusMissaoEnum;
import org.infnet.guildaApiTP1.common.exceptions.EntityNotFoundException;
import org.infnet.guildaApiTP1.common.exceptions.RegraDeMissaoException;
import org.infnet.guildaApiTP1.aventura.model.Aventureiro;
import org.infnet.guildaApiTP1.aventura.model.Missao;
import org.infnet.guildaApiTP1.aventura.model.ParticipacaoEmMissao;
import org.infnet.guildaApiTP1.aventura.repository.AventureiroRepository;
import org.infnet.guildaApiTP1.aventura.repository.MissaoRepository;
import org.infnet.guildaApiTP1.aventura.repository.ParticipacaoEmMissaoRepository;
import org.infnet.guildaApiTP1.common.util.ValidarDataIntervaloUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
@RequiredArgsConstructor
public class ParticipacaoEmMissaoService {
    private final MissaoRepository missaoRepository;
    private final AventureiroRepository aventureiroRepository;
    private final ParticipacaoEmMissaoRepository participacaoRepository;

    @Transactional
    public ParticipacaoEmMissao criarParticipacaoEmMissao(CriarParticipacaoEmMissaoRequest dto) {
        Missao missao = missaoRepository.findById(dto.missaoId())
                .orElseThrow(() -> new EntityNotFoundException("Missão não encontrada"));

        Aventureiro aventureiro = aventureiroRepository.findById(dto.aventureiroId())
                .orElseThrow(() -> new EntityNotFoundException("Aventureiro não encontrado"));

        validar(aventureiro.pertenceAOrganizacao(missao.getOrganizacao().getId()),
                "O aventureiro não pertence à organização da missão");
        validar(missao.aceitaNovosParticipantes(),
                "A missão não está aceitando novos participantes");
        validar(aventureiro.isAtivo(),
                "O aventureiro deve estar ativo para participar de uma missão");
        validar(!participacaoRepository.existsByMissaoIdAndAventureiroId(dto.missaoId(), dto.aventureiroId()),
                "O aventureiro já está participando dessa missão");

        ParticipacaoEmMissao participacao = new ParticipacaoEmMissao();
        participacao.setMissao(missao);
        participacao.setAventureiro(aventureiro);
        participacao.setPapelNaMissao(dto.papelNaMissao());
        participacao.setRecompensaEmOuro(dto.recompensaEmOuro());
        participacao.setDestaqueMvp(dto.destaqueMvp());

        return participacaoRepository.save(participacao);
    }

    private void validar(Boolean condicao, String mensagem) {
        if (!condicao) {
            throw new RegraDeMissaoException(mensagem);
        }
    }

    public Page<RelatorioParticipacaoDTO> gerarRankingAventureiros(
            StatusMissaoEnum status,
            ZonedDateTime dataInicio,
            ZonedDateTime dataFim) {

        ZonedDateTime dataIntervaloInicio = ValidarDataIntervaloUtils.intervaloInicioOuPadrao(dataInicio);
        ZonedDateTime dataIntervaloFim = ValidarDataIntervaloUtils.intervaloFimOuPadrao(dataFim);

        return participacaoRepository.gerarRankingAventureiros(
                status,
                dataIntervaloInicio,
                dataIntervaloFim,
                PageRequest.of(0, 10)
        );
    }

    public Page<RelatorioMissaoDTO> gerarRelatorioMissao(ZonedDateTime dataInicio, ZonedDateTime dataFim, Pageable pageable) {

        ZonedDateTime dataIntervaloInicio = ValidarDataIntervaloUtils.intervaloInicioOuPadrao(dataInicio);
        ZonedDateTime dataIntervaloFim = ValidarDataIntervaloUtils.intervaloFimOuPadrao(dataFim);

        return participacaoRepository.gerarRelatorioMissao(
                dataIntervaloInicio,
                dataIntervaloFim,
                pageable);
    }
}
