package um.haberes.core.hexagonal.liquidaciones.categoria.application.exception;

public class CategoriaException extends RuntimeException {

    private static final long serialVersionUID = 7710146162234020015L;

    public CategoriaException() {
        super("Cannot find Categoria");
    }

    public CategoriaException(Integer categoriaId) {
        super("Cannot find Categoria " + categoriaId);
    }

    public CategoriaException(String message) {
        super(message);
    }
}
