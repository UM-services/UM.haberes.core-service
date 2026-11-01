package um.haberes.core.hexagonal.cursos.curso_fusion.application.exception;

public class CursoFusionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CursoFusionException() {
        super("Cannot find CursoFusion");
    }

    public CursoFusionException(Long cursoFusionId) {
        super("Cannot find CursoFusion " + cursoFusionId);
    }

    public CursoFusionException(String message) {
        super(message);
    }
}
