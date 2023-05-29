/**
 * 
 */
package um.haberes.rest.exception.view;

/**
 * @author daniel
 *
 */
public class TotalSalidaNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4982296773847324000L;

	public TotalSalidaNotFoundException () {
		super("Cannot find TotalSalida");
	}

	public TotalSalidaNotFoundException (Integer anho, Integer mes) {
		super("Cannot find TotalSalida " + mes + "/" + anho);
	}
}
