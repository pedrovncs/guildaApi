package org.infnet.guildaApiTP1.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.dto.AventureiroDTO;
import org.infnet.guildaApiTP1.dto.AventureiroResumoDTO;
import org.infnet.guildaApiTP1.dto.BuscaResponse;
import org.infnet.guildaApiTP1.dto.CompanheiroDTO;
import org.infnet.guildaApiTP1.enums.ClasseEnum;
import org.infnet.guildaApiTP1.model.Aventureiro;
import org.infnet.guildaApiTP1.service.AventureiroService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/aventureiros")
@RequiredArgsConstructor
public class AventureiroController {

    private final AventureiroService service;

    @PostMapping
    public ResponseEntity<Aventureiro> registrar(@RequestBody @Valid AventureiroDTO aventureiroDTO){
        Aventureiro aventureiro = service.registrar(aventureiroDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(aventureiro);
    }

    @GetMapping
    public ResponseEntity<List<AventureiroResumoDTO>> listarTodos(
            @RequestParam(required = false) ClasseEnum classe,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer nivelMinimo,
            @RequestHeader(value = "X-Page", defaultValue = "0") @Min(0) int page,
            @RequestHeader(value = "X-Size", defaultValue = "10") @Min(1) @Max(50) int size
    ){
        BuscaResponse<Aventureiro> resultado = service.buscarTodos(classe, ativo, nivelMinimo, page, size);

        List<AventureiroResumoDTO> resumoDTOS = resultado.conteudo().stream()
                .map(a -> new AventureiroResumoDTO(
                        a.getId(),
                        a.getNome(),
                        a.getClasse(),
                        a.getNivel(),
                        a.isAtivo()
                )).toList();

        return ResponseEntity.status(HttpStatus.OK)
                .header("X-Page",String.valueOf(page))
                .header("X-Size", String.valueOf(size))
                .header("X-Page-Count", String.valueOf(resultado.totalPaginas()))
                .header("X-Total-Count",String.valueOf(resultado.totalItens()))
                .body(resumoDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aventureiro> buscar(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.buscarPorId(id));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> alterarStatus(@PathVariable Long id, @RequestParam boolean ativo) {
        service.atualizarStatus(id, ativo);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Aventureiro> atualizar(
            @PathVariable Long id,
            @RequestBody @Valid AventureiroDTO dto) {

        Aventureiro atualizado = service.atualizar(id, dto);

        return ResponseEntity.status(HttpStatus.OK).body(atualizado);
    }


    @PutMapping("/{id}/companheiro")
    public ResponseEntity<Void> definirCompanheiro(
            @PathVariable Long id,
            @RequestBody @Valid CompanheiroDTO dto) {

        service.definirCompanheiro(id, dto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Void> removerCompanheiro(@PathVariable Long id) {
        service.removerCompanheiro(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
