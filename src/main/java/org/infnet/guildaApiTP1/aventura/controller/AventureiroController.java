package org.infnet.guildaApiTP1.aventura.controller;

import org.infnet.guildaApiTP1.aventura.dto.AventureiroCompletoDTO;
import org.infnet.guildaApiTP1.aventura.dto.AventureiroDTO;
import org.infnet.guildaApiTP1.aventura.dto.request.AtualizarAventureiroRequest;
import org.infnet.guildaApiTP1.aventura.dto.request.CriarAventureiroRequest;
import org.infnet.guildaApiTP1.aventura.dto.request.CriarCompanheiroRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.aventura.enums.ClasseEnum;
import org.infnet.guildaApiTP1.aventura.service.AventureiroService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/aventureiros")
@RequiredArgsConstructor
public class AventureiroController {

    private final AventureiroService aventureiroService;

    @PostMapping
    public ResponseEntity<AventureiroDTO> criarAventureiro(@Valid @RequestBody CriarAventureiroRequest dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(aventureiroService.criarAventureiro(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AventureiroDTO> atualizarAventureiro(
            @Valid @RequestBody AtualizarAventureiroRequest dto,
            @PathVariable Long id){

        return ResponseEntity.status(HttpStatus.OK).body(aventureiroService.atualizarAventureiro(id, dto));
    }

    @PostMapping(("/{id}/desvincular"))
    public ResponseEntity<Void> encerrarVinculo(@PathVariable Long id){
        aventureiroService.encerrarVinculo(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(("/{id}/revincular"))
    public ResponseEntity<Void> renovarVinculo(@PathVariable Long id){
        aventureiroService.renovarVinculo(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<AventureiroDTO>> listarAventureiros(
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) ClasseEnum classe,
            @RequestParam(required = false) Integer nivelMinimo,
            @RequestParam(defaultValue = "0") @Min(0) int page,
            @RequestParam(defaultValue = "10") @Min(1) @Max(50) int size
    ) {
        Pageable pageable = PageRequest.of(page, size);

        Page<AventureiroDTO> aventureiros = aventureiroService.listarAventureirosComFiltros(
                ativo,
                classe,
                nivelMinimo,
                pageable
        );

        return ResponseEntity.status(HttpStatus.OK).body(aventureiros);
    }

    @PutMapping("/{id}/companheiro")
    public ResponseEntity<Void> definirCompanheiro(
            @Valid @RequestBody CriarCompanheiroRequest request,
            @PathVariable Long id) {
        aventureiroService.definirCompanheiro(id, request);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Void> removerCompanheiro(@PathVariable Long id){
        aventureiroService.removerCompanheiro(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AventureiroCompletoDTO> buscarAventureiroPorId(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(aventureiroService.buscarAventureiroCompleto(id));
    }


}
