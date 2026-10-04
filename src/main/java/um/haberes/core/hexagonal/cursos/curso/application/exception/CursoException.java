package um.haberes.core.hexagonal.cursos.curso.application.exception;

public class CursoException extends RuntimeException {

    private static final long serialVersionUID = 9172028542868166926L;

    public CursoException() {
        super("Cannot find Curso");
    }

    public CursoException(Long cursoId) {
        super("Cannot find Curso " + cursoId);
    }

}
