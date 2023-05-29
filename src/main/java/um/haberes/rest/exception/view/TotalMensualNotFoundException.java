/**
 * 
 */
package um.haberes.rest.exception.view;

/**
 * @author daniel
 *
 */
public class TotalMensualNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8378750232085812054L;

	public TotalMensualNotFoundException(Integer anho, Integer mes, Integer codigoId) {
		super("Cannot found TotalMensual " + anho + "/" + mes + "/" + codigoId);
	}

}
