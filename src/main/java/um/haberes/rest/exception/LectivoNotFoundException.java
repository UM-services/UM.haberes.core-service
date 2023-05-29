/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class LectivoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3607683756074817437L;

	public LectivoNotFoundException(Integer lectivoId) {
		super("Cannot found Lectivo " + lectivoId);
	}

}
