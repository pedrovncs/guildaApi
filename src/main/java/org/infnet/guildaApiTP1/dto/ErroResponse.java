package org.infnet.guildaApiTP1.dto;

import java.util.List;

public record ErroResponse(String mensagem, List<String> detalhes) {}