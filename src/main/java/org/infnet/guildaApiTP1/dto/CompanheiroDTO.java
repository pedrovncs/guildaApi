package org.infnet.guildaApiTP1.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.infnet.guildaApiTP1.enums.EspecieEnum;

public record CompanheiroDTO(
        @NotBlank(message = "O nome deve ser informado")
        String nome,

        @NotNull(message = "A espécie deve ser informada")
        EspecieEnum especie,

        @NotNull(message = "A lealdade deve ser informada")
        @Min(value = 0, message = "Lealdade mínima é 0")
        @Max(value = 100, message = "A lealdade máxima é 100")
        Integer lealdade) {
}
