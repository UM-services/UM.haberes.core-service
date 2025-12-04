/**
 * 
 */
package um.haberes.core.exception.view;

/**
 * @author daniel
 *
 */
public class TotalMensualException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8378750232085812054L;

	public TotalMensualException(Integer anho, Integer mes, Integer codigoId) {
		super("Cannot found TotalMensual " + anho + "/" + mes + "/" + codigoId);
	}

}
