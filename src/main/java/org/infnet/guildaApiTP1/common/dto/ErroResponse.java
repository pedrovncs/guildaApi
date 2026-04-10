package org.infnet.guildaApiTP1.common.dto;

import java.util.List;

public record ErroResponse(
        String mensagem,
        List<String> detalhes) {}