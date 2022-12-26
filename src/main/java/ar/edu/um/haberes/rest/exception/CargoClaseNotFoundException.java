/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CargoClaseNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5929880875063849507L;

	public CargoClaseNotFoundException(Long cargoclaseId) {
		super("Cannot find CargoClase " + cargoclaseId);
	}

}
