/**
 * 
 */
package um.haberes.core.exception.view;

/**
 * @author daniel
 *
 */
public class TotalSalidaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4982296773847324000L;

	public TotalSalidaException() {
		super("Cannot find TotalSalida");
	}

	public TotalSalidaException(Integer anho, Integer mes) {
		super("Cannot find TotalSalida " + mes + "/" + anho);
	}
}
