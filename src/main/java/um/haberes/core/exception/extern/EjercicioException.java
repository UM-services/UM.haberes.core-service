package um.haberes.core.exception.extern;

import java.time.OffsetDateTime;

public class EjercicioException extends RuntimeException{

    public EjercicioException(OffsetDateTime lastDate) {
        super("Cannot find Ejercicio for " + lastDate);
    }
}
