package org.infnet.guildaApiTP1.aventura.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.infnet.guildaApiTP1.aventura.enums.ClasseEnum;

public record AtualizarAventureiroRequest(
        @NotBlank(message = "Nome deve ser informado")
        String nome,

        @NotNull(message = "Classe deve ser informada")
        ClasseEnum classe,

        @NotNull(message = "Nível deve ser informado")
        @Min(value = 1, message = "Nível mínimo é 1")
        Integer nivel
) {
}