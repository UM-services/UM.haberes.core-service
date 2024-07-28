/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class CargoClaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5929880875063849507L;

	public CargoClaseException(Long cargoclaseId) {
		super("Cannot find CargoClase " + cargoclaseId);
	}

}
