package org.infnet.guildaApiTP1.scheduler;

import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.service.PainelTaticoMissaoService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisScheduler {
    private final PainelTaticoMissaoService painelTaticoService;

    @Scheduled(cron = "0 33 3 * * *")
    //@Scheduled(fixedRate = 10000)
    public void limparCache(){
        painelTaticoService.evictCache();
        painelTaticoService.buscarTop10();
        System.out.println("Cache foi atualizado.");
    }
}
