/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class CargoClaseDetalleException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2654780766336048535L;

	public CargoClaseDetalleException(Long cargoclasedetalleId) {
		super("Cannot find CargoClaseDetalle " + cargoclasedetalleId);
	}

}
