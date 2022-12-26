/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CargoTipoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1396773511411721648L;

	public CargoTipoNotFoundException(Integer cargotipoId) {
		super("Cannot fin CargoTipo " + cargotipoId);
	}

}
