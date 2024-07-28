/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class LectivoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3607683756074817437L;

	public LectivoException(Integer lectivoId) {
		super("Cannot found Lectivo " + lectivoId);
	}

}
