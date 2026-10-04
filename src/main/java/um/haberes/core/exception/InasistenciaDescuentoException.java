package um.haberes.core.exception;

public class InasistenciaDescuentoException extends RuntimeException {

    public InasistenciaDescuentoException(Integer facultadId, Integer geograficaId, Integer inasistencias) {
        super("InasistenciaDescuentoEntity not found for " + facultadId + " " + geograficaId + " " + inasistencias);
    }

}
