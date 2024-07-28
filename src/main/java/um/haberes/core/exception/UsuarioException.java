/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class UsuarioException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7193939140059073431L;

	public UsuarioException(Long legajoId) {
		super("Cannot find Usuario " + legajoId);
	}

}
