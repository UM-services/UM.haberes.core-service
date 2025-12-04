/**
 * 
 */
package um.haberes.core.exception.view;

/**
 * @author daniel
 *
 */
public class CargoClasePeriodoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2678656838708930181L;

	public CargoClasePeriodoException(Long cargoclaseperiodoId) {
		super("Cannot find CargoClasePeriodo " + cargoclaseperiodoId);
	}

}
