package org.infnet.guildaApiTP1.service;

import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.dto.PainelTaticoMissaoDTO;
import org.infnet.guildaApiTP1.repository.PainelTaticoMissaoRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PainelTaticoMissaoService {
    private final PainelTaticoMissaoRepository painelTaticoMissaoRepository;

    public List<PainelTaticoMissaoDTO> buscarTop10() {
        LocalDateTime dataMinima = LocalDateTime.now().minusDays(15);

        return painelTaticoMissaoRepository.buscarTop10DosUltimos15Dias(
                dataMinima,
                PageRequest.of(0,10));
    }
}
