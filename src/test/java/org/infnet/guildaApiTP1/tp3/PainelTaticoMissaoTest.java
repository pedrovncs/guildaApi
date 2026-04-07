package org.infnet.guildaApiTP1.tp3;

import org.infnet.guildaApiTP1.dto.PainelTaticoMissaoDTO;
import org.infnet.guildaApiTP1.service.PainelTaticoMissaoService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.context.annotation.Import;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import({PainelTaticoMissaoService.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PainelTaticoMissaoTest {

    @Autowired
    private PainelTaticoMissaoService painelTaticoService;

    @Test
    @DisplayName("deve retornar exatamente 10 missoes")
    void deveRetornar10Missoes(){

        List<PainelTaticoMissaoDTO> top10 = painelTaticoService.buscarTop10();

        assertEquals(10, top10.size());
        top10.stream()
                .map(PainelTaticoMissaoDTO::id)
                .forEach(Assertions::assertNotNull);
    }

}
