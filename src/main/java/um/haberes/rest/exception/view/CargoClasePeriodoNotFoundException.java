/**
 * 
 */
package um.haberes.rest.exception.view;

/**
 * @author daniel
 *
 */
public class CargoClasePeriodoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2678656838708930181L;

	public CargoClasePeriodoNotFoundException(Long cargoclaseperiodoId) {
		super("Cannot find CargoClasePeriodo " + cargoclaseperiodoId);
	}

}
