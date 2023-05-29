/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CursoDesarraigoNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3007217152941479441L;

	public CursoDesarraigoNotFoundException() {
		super("Could not find CursoDesarraigo");
	}

	public CursoDesarraigoNotFoundException(Long cursodesarraigoID) {
		super("Could not find CursoDesarraigo " + cursodesarraigoID);
	}

	public CursoDesarraigoNotFoundException(Long legajoID, Integer anho, Integer mes, Long cursoID) {
		super("Could not find CursoDesarraigo " + legajoID + "/" + anho + "/" + mes + "/" + cursoID);
	}
}
