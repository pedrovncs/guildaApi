package org.infnet.guildaApiTP1.service;

import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.repository.AventureiroRepository;
import org.infnet.guildaApiTP1.repository.MissaoRepository;
import org.infnet.guildaApiTP1.repository.ParticipacaoEmMissaoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissaoService {
    private final MissaoRepository missaoRepository;
    private final AventureiroRepository aventureiroRepository;
    private final ParticipacaoEmMissaoRepository participacaoRepository;

}
