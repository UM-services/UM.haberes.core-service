package um.haberes.core.exception;

import java.text.MessageFormat;

public class LiquidacionAdicionalException extends RuntimeException {
    public LiquidacionAdicionalException(Long legajoId, Integer anho, Integer mes, Integer dependenciaId) {
        super(MessageFormat.format("Cannot find LiquidacionAdicional -> {0}/{1}/{2}/{3}", legajoId, anho, mes, dependenciaId));
    }

}
