package um.haberes.core.hexagonal.geografica.application.exception;

public class GeograficaException extends RuntimeException {

    public GeograficaException(Integer id) {
        super("No se pudo encontrar la Geográfica con id: " + id);
    }
}
