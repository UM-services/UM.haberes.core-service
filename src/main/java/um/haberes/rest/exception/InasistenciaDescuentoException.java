package um.haberes.rest.exception;

public class InasistenciaDescuentoException extends RuntimeException {

    public InasistenciaDescuentoException(Integer facultadId, Integer geograficaId, Integer inasistencias) {
        super("InasistenciaDescuento not found for " + facultadId + " " + geograficaId + " " + inasistencias);
    }

}
