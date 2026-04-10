package org.infnet.guildaApiTP1.loja.dto;

public record ProdutoDTO(
        String id,
        String categoria,
        String descricao,
        String nome,
        String raridade,
        Float preco) {
}
