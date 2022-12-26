/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author dquinteros
 *
 */
public class GeograficaNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6154291399997275882L;

	public GeograficaNotFoundException(Integer geograficaId) {
		super("Cannot find Geografica " + geograficaId);
	}
}
