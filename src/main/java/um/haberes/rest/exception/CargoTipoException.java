/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CargoTipoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1396773511411721648L;

	public CargoTipoException(Integer cargotipoId) {
		super("Cannot fin CargoTipo " + cargotipoId);
	}

}
