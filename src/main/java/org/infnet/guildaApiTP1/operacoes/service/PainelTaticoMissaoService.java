package org.infnet.guildaApiTP1.operacoes.service;

import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.operacoes.dto.PainelTaticoMissaoDTO;
import org.infnet.guildaApiTP1.operacoes.repository.PainelTaticoMissaoRepository;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PainelTaticoMissaoService {
    private final PainelTaticoMissaoRepository painelTaticoMissaoRepository;

    @Cacheable(cacheNames = "PainelTaticoMissaoTop10", key = "'15dias'")
    public List<PainelTaticoMissaoDTO> buscarTop10() {
        LocalDateTime dataMinima = LocalDateTime.now().minusDays(15);

        return painelTaticoMissaoRepository.buscarTop10DosUltimos15Dias(
                dataMinima,
                PageRequest.of(0,10));
    }

    @CacheEvict(cacheNames = "PainelTaticoMissaoTop10", allEntries = true)
    public void evictCache() {
    }
}
