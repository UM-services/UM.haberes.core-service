package um.haberes.core.hexagonal.cursos.curso_desarraigo.application.exception;

public class CursoDesarraigoException extends RuntimeException {

    private static final long serialVersionUID = 3007217152941479441L;

    public CursoDesarraigoException() {
        super("Could not find CursoDesarraigo");
    }

    public CursoDesarraigoException(Long cursoDesarraigoId) {
        super("Could not find CursoDesarraigo " + cursoDesarraigoId);
    }

    public CursoDesarraigoException(Long legajoId, Integer anho, Integer mes, Long cursoId) {
        super("Could not find CursoDesarraigo " + legajoId + "/" + anho + "/" + mes + "/" + cursoId);
    }
}
