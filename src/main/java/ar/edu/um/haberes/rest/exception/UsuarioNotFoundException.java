/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class UsuarioNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7193939140059073431L;

	public UsuarioNotFoundException(Long legajoId) {
		super("Cannot find Usuario " + legajoId);
	}

}
