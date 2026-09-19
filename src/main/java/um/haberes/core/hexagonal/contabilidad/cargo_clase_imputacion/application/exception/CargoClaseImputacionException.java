package um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.application.exception;

public class CargoClaseImputacionException extends RuntimeException {

    private static final long serialVersionUID = -774259631502621009L;

    public CargoClaseImputacionException(Long cargoClaseImputacionId) {
        super("No se pudo encontrar el CargoClaseImputacion con id: " + cargoClaseImputacionId);
    }

    public CargoClaseImputacionException(Integer dependenciaId, Integer facultadId, Integer geograficaId,
            Long cargoClaseId) {
        super("No se pudo encontrar el CargoClaseImputacion " + dependenciaId + "/" + facultadId + "/" + geograficaId
                + "/" + cargoClaseId);
    }
}
