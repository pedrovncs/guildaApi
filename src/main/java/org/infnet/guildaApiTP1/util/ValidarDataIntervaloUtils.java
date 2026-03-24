package org.infnet.guildaApiTP1.util;

import java.time.ZonedDateTime;

public class ValidarDataIntervaloUtils {

    public static ZonedDateTime intervaloInicioOuPadrao(ZonedDateTime data) {
        return data != null
                ? data
                : ZonedDateTime.of(1, 1, 1, 0, 0, 0, 0, ZonedDateTime.now().getZone());
    }

    public static ZonedDateTime intervaloFimOuPadrao(ZonedDateTime data) {
        return data != null
                ? data
                : ZonedDateTime.of(9999, 1, 1, 0, 0, 0, 0, ZonedDateTime.now().getZone());
    }
}
