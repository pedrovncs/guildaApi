package org.infnet.guildaApiTP1.service;

import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.dto.AventureiroDTO;
import org.infnet.guildaApiTP1.enums.ClasseEnum;
import org.infnet.guildaApiTP1.repository.AventureiroRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AventureiroService {
    private final AventureiroRepository aventureiroRepository;

    public Page<AventureiroDTO> listarAventureirosComFiltros(Boolean ativo, ClasseEnum classe, Integer nivelMinimo, Pageable pageable) {
        return aventureiroRepository.buscarComFiltros(ativo, classe, nivelMinimo, pageable)
                .map(a -> new AventureiroDTO(
                        a.getId(),
                        a.getNome(),
                        a.getClasse(),
                        a.getNivel(),
                        a.isAtivo()
                ));
    }
}
