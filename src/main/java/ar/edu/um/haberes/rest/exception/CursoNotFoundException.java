/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CursoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 9172028542868166926L;
	
	public CursoNotFoundException(Long cursoId) {
		super("Cannot find Curso " + cursoId);
	}

}
