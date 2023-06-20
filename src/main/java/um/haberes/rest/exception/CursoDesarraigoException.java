/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CursoDesarraigoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3007217152941479441L;

	public CursoDesarraigoException() {
		super("Could not find CursoDesarraigo");
	}

	public CursoDesarraigoException(Long cursodesarraigoID) {
		super("Could not find CursoDesarraigo " + cursodesarraigoID);
	}

	public CursoDesarraigoException(Long legajoID, Integer anho, Integer mes, Long cursoID) {
		super("Could not find CursoDesarraigo " + legajoID + "/" + anho + "/" + mes + "/" + cursoID);
	}
}
