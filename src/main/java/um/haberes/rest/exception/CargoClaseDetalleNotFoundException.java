/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CargoClaseDetalleNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2654780766336048535L;

	public CargoClaseDetalleNotFoundException(Long cargoclasedetalleId) {
		super("Cannot find CargoClaseDetalle " + cargoclasedetalleId);
	}

}
