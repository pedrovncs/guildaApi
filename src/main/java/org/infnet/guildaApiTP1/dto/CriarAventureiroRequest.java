package org.infnet.guildaApiTP1.dto;

import jakarta.validation.constraints.*;
import org.infnet.guildaApiTP1.enums.ClasseEnum;

public record CriarAventureiroRequest(
        @NotBlank(message = "Nome deve ser informado")
        String nome,
        @NotNull(message = "Classe deve ser informada corretamente.")
        ClasseEnum classe,
        @NotNull(message = "Nível deve ser informado.")
        @Min(message = "Nível informado deve ser no mínimo 1", value = 1)
        Integer nivel
        ) {
}
