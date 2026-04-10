package org.infnet.guildaApiTP1.loja.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.AggregationRange;
import co.elastic.clients.elasticsearch._types.aggregations.StringTermsBucket;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import lombok.RequiredArgsConstructor;
import org.infnet.guildaApiTP1.loja.dto.FaixaDePrecoAggsDTO;
import org.infnet.guildaApiTP1.loja.dto.PrecoMedioDTO;
import org.infnet.guildaApiTP1.loja.dto.ProdutoAggsDTO;
import org.infnet.guildaApiTP1.loja.dto.ProdutoDTO;
import org.infnet.guildaApiTP1.loja.model.ProdutoDocument;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProdutoService {
    private final ElasticsearchClient client;
    private static final String INDEX = "guilda_loja";

    private ProdutoDTO toProdutoDTO(ProdutoDocument p){
        return new ProdutoDTO(
                p.getId(),
                p.getCategoria(),
                p.getDescricao(),
                p.getNome(),
                p.getRaridade(),
                p.getPreco()
        );
    }

    private List<ProdutoDTO> toProdutoDTOList(SearchResponse<ProdutoDocument> response) {
        return response.hits().hits().stream()
                .map(Hit::source)
                .filter(Objects::nonNull)
                .map(this::toProdutoDTO)
                .toList();
    }

    private ProdutoAggsDTO toProdutoAggsDTO(StringTermsBucket p){
        return new ProdutoAggsDTO(
                p.key().stringValue(),
                p.docCount()
        );
    }

    private List<ProdutoAggsDTO> toProdutoAggsDTOList(SearchResponse<Void> response, String aggName){
        return response.aggregations()
                .get(aggName)
                .sterms()
                .buckets()
                .array()
                .stream()
                .map(this::toProdutoAggsDTO)
                .toList();
    }
    //a
    public List<ProdutoDTO> buscarPorNome(String termo) throws IOException {
        SearchResponse<ProdutoDocument> response = client.search(s -> s
                .index(INDEX)
                .query(q -> q
                        .match(m -> m.field("nome").query(termo))
                ), ProdutoDocument.class);

        return toProdutoDTOList(response);
    }

    public List<ProdutoDTO> buscarPorDescricao(String termo) throws IOException {
        SearchResponse<ProdutoDocument> response = client.search(s -> s
                .index(INDEX)
                .query(q -> q
                        .match(m -> m.field("descricao").query(termo))
                ), ProdutoDocument.class);
        return toProdutoDTOList(response);
    }

    public List<ProdutoDTO> buscarFraseExataNaDescricao(String termo) throws IOException {
        SearchResponse<ProdutoDocument> response = client.search(s -> s
                .index(INDEX)
                .query(q -> q
                        .matchPhrase(m -> m.field("descricao").query(termo))
                ), ProdutoDocument.class);
        return toProdutoDTOList(response);
    }

    public List<ProdutoDTO> buscarPorNomeComFuzzy(String termo) throws IOException {
        SearchResponse<ProdutoDocument> response = client.search(s -> s
                .index(INDEX)
                .query(q -> q
                        .match(m -> m
                                .field("nome")
                                .query(termo)
                                .fuzziness("AUTO"))
                ), ProdutoDocument.class);
        return toProdutoDTOList(response);
    }

    public List<ProdutoDTO> buscarEmNomeEDescricao(String termo) throws IOException {
        SearchResponse<ProdutoDocument> response = client.search(s -> s
                .index(INDEX)
                .query(q -> q
                        .multiMatch(mm -> mm
                                .query(termo)
                                .fields("nome", "descricao")
                        )
                ), ProdutoDocument.class);
        return toProdutoDTOList(response);
    }
    //b
    public List<ProdutoDTO> buscarPorDescricaoComFiltroCategoria(String termo, String categoria) throws IOException {
        SearchResponse<ProdutoDocument> response = client.search(s -> s
                .index(INDEX)
                .query(q -> q
                                .bool(b -> b
                                        .must(m -> m
                                                .match(mm -> mm.field("descricao").query(termo)))
                                        .filter(f -> f
                                                .term(t -> t.field("categoria").value(categoria))))
                ), ProdutoDocument.class);
        return toProdutoDTOList(response);
    }

    public List<ProdutoDTO> buscarPorFaixaPreco(Double min, Double max) throws IOException {
        SearchResponse<ProdutoDocument> response = client.search(s -> s
                .index(INDEX)
                .query(q -> q
                        .bool(b -> b
                                .filter(f -> f
                                        .range(r -> r
                                                .number(n -> n.field("preco").gte(min).lte(max)))))
                ), ProdutoDocument.class);
        return toProdutoDTOList(response);
    }

    public List<ProdutoDTO> buscarPorCategoriaRaridadeFaixaDePreco(String categoria, String raridade, Double min, Double max) throws IOException {
        SearchResponse<ProdutoDocument> response = client.search(s -> s
                .index(INDEX)
                .query(q -> q
                        .bool(b -> b
                                .filter(f -> f
                                        .term(t -> t.field("categoria").value(categoria)))
                                .filter(f -> f
                                        .term(t -> t.field("raridade").value(raridade)))
                                .filter(f -> f
                                        .term(t -> t.field("categoria").value(categoria)))
                                .filter(f -> f
                                        .range(r -> r
                                                .number(n -> n.field("preco").gte(min).lte(max)))))
                ), ProdutoDocument.class);
        return toProdutoDTOList(response);
    }
    //c
    public List<ProdutoAggsDTO> agregarPorCategoria() throws IOException {
        String aggName = "por_categoria";
        SearchResponse<Void> response = client.search(s -> s
                .index(INDEX)
                .size(0)
                .aggregations(aggName, a -> a
                        .terms(t -> t.field("categoria"))
                ), Void.class);

        return toProdutoAggsDTOList(response, aggName);
    }

    public List<ProdutoAggsDTO> agregarPorRaridade() throws IOException {
        String aggName = "por_raridade";
        SearchResponse<Void> response = client.search(s -> s
                .index(INDEX)
                .size(0)
                .aggregations(aggName, a -> a
                        .terms(t -> t.field("raridade"))
                ), Void.class);

        return toProdutoAggsDTOList(response, aggName);
    }

    public PrecoMedioDTO agregarPrecoMedio() throws IOException {
        String aggName = "preco_medio_produtos";
        SearchResponse<Void> response = client.search(s -> s
                .index(INDEX)
                .size(0)
                .aggregations(aggName, a -> a
                        .avg( avg -> avg.field("preco"))
                ), Void.class);

        Double avg = response.aggregations().get(aggName).avg().value();

        return new PrecoMedioDTO(avg);
    }

    public List<FaixaDePrecoAggsDTO> agregarPorFaixaDePreco() throws IOException {
        String aggName = "por_faixa_de_preco_produtos";

        List<AggregationRange> faixas_preco = List.of(
                AggregationRange.of(r -> r.key("Abaixo de 100").to(100.0)),
                AggregationRange.of(r -> r.key("De 100 a 300").from(100.0).to(300.0)),
                AggregationRange.of(r -> r.key("De 300 a 700").from(300.0).to(700.0)),
                AggregationRange.of(r -> r.key("Acima de 700").from(700.0))
        );

        SearchResponse<Void> response = client.search(s -> s
                .index(INDEX)
                .size(0)
                .aggregations(aggName, a -> a
                        .range(r -> r
                                .field("preco")
                                .ranges(faixas_preco))
                ), Void.class);

        return response.aggregations().get(aggName)
                .range()
                .buckets()
                .array()
                .stream()
                .map(b ->
                        new FaixaDePrecoAggsDTO(
                                b.key(),
                                b.from(),
                                b.to(),
                                b.docCount()))
                .toList();
    }
}
