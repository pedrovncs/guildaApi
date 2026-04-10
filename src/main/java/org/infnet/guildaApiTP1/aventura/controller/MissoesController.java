package org.infnet.guildaApiTP1.aventura.controller;

import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.operacoes.dto.PainelTaticoMissaoDTO;
import org.infnet.guildaApiTP1.operacoes.service.PainelTaticoMissaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/missoes")
@RequiredArgsConstructor
public class MissoesController {
    private final PainelTaticoMissaoService painelTaticoService;

    @GetMapping("/top15dias")
    public ResponseEntity<List<PainelTaticoMissaoDTO>> top15dias(){
        return ResponseEntity.status(HttpStatus.OK).body(painelTaticoService.buscarTop10());
    }
}
