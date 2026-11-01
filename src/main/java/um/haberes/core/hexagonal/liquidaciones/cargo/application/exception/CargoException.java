package um.haberes.core.hexagonal.liquidaciones.cargo.application.exception;

public class CargoException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CargoException() {
        super("Cargo not found");
    }

    public CargoException(Long cargoId) {
        super("Cannot find Cargo " + cargoId);
    }
}
