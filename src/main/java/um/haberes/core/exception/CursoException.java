/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class CursoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9172028542868166926L;
	
	public CursoException(Long cursoId) {
		super("Cannot find Curso " + cursoId);
	}

}
