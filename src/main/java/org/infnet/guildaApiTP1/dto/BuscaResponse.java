package org.infnet.guildaApiTP1.dto;

import java.util.List;

public record BuscaResponse<T>(
        List<T> conteudo,
        int totalItens,
        int totalPaginas,
        int paginaAtual
) {

}
