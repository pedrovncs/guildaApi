package org.infnet.guildaApiTP1.controller;

import lombok.RequiredArgsConstructor;

import org.infnet.guildaApiTP1.dto.FaixaDePrecoAggsDTO;
import org.infnet.guildaApiTP1.dto.PrecoMedioDTO;
import org.infnet.guildaApiTP1.dto.ProdutoAggsDTO;
import org.infnet.guildaApiTP1.dto.ProdutoDTO;
import org.infnet.guildaApiTP1.service.ProdutoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {
    private final ProdutoService produtoService;

    @GetMapping("/busca/nome")
    public ResponseEntity<List<ProdutoDTO>> buscarPorNome(@RequestParam String termo) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.buscarPorNome(termo));
    }

    @GetMapping("/busca/descricao")
    public ResponseEntity<List<ProdutoDTO>> buscarPorDescricao(@RequestParam String termo) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.buscarPorDescricao(termo));
    }

    @GetMapping("/busca/frase")
    public ResponseEntity<List<ProdutoDTO>> buscarPorFraseExata(@RequestParam String termo) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.buscarFraseExataNaDescricao(termo));
    }

    @GetMapping("/busca/fuzzy")
    public ResponseEntity<List<ProdutoDTO>> buscarNomeFuzzy(@RequestParam String termo) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.buscarPorNomeComFuzzy(termo));
    }

    @GetMapping("/busca/multicampos")
    public ResponseEntity<List<ProdutoDTO>> buscarNomeDescricao(@RequestParam String termo) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.buscarEmNomeEDescricao(termo));
    }

    @GetMapping("/busca/com-filtro")
    public ResponseEntity<List<ProdutoDTO>> buscarDescricaoComFiltroCategoria(
            @RequestParam String termo,
            @RequestParam String categoria) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.buscarPorDescricaoComFiltroCategoria(termo, categoria));
    }

    @GetMapping("/busca/faixa-preco")
    public ResponseEntity<List<ProdutoDTO>> buscarComFaixaDePreco(
            @RequestParam Double min,
            @RequestParam Double max) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.buscarPorFaixaPreco(min, max));
    }

    @GetMapping("/busca/avancada")
    public ResponseEntity<List<ProdutoDTO>> buscarComFaixaDePreco(
            @RequestParam String categoria,
            @RequestParam String raridade,
            @RequestParam Double min,
            @RequestParam Double max) throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.buscarPorCategoriaRaridadeFaixaDePreco(
                categoria, raridade, min, max));
    }

    @GetMapping("/agregacoes/por-categoria")
    public ResponseEntity<List<ProdutoAggsDTO>> agregarPorCategoria() throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.agregarPorCategoria());
    }

    @GetMapping("/agregacoes/por-raridade")
    public ResponseEntity<List<ProdutoAggsDTO>> agregarPorRaridade() throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.agregarPorRaridade());
    }

    @GetMapping("/agregacoes/preco-medio")
    public ResponseEntity<PrecoMedioDTO> agregarPrecoMedioProdutos() throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.agregarPrecoMedio());
    }

    @GetMapping("/agregacoes/faixas-preco")
    public ResponseEntity<List<FaixaDePrecoAggsDTO>> agregarPorFaixaDePreco() throws IOException {
        return ResponseEntity.status(HttpStatus.OK).body(produtoService.agregarPorFaixaDePreco());
    }


}
