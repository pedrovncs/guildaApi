package org.infnet.guildaApiTP1.loja.dto;

public record FaixaDePrecoAggsDTO(
        String descricaoFaixaPreco,
        Double de,
        Double ate,
        long totalProdutos
) {
}
