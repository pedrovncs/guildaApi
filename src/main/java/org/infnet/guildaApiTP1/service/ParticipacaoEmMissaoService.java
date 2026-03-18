package org.infnet.guildaApiTP1.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.dto.CriarParticipacaoEmMissaoRequest;
import org.infnet.guildaApiTP1.exceptions.EntityNotFoundException;
import org.infnet.guildaApiTP1.exceptions.RegraDeMissaoException;
import org.infnet.guildaApiTP1.model.aventura_schema.Aventureiro;
import org.infnet.guildaApiTP1.model.aventura_schema.Missao;
import org.infnet.guildaApiTP1.model.aventura_schema.ParticipacaoEmMissao;
import org.infnet.guildaApiTP1.repository.AventureiroRepository;
import org.infnet.guildaApiTP1.repository.MissaoRepository;
import org.infnet.guildaApiTP1.repository.ParticipacaoEmMissaoRepository;
import org.springframework.stereotype.Service;

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
}
