/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author dquinteros
 *
 */
public class GeograficaException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6154291399997275882L;

	public GeograficaException(Integer geograficaId) {
		super("Cannot find Geografica " + geograficaId);
	}
}
