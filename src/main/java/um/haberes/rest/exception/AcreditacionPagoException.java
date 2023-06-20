/**
 * 
 */
package um.haberes.rest.exception;

import java.time.OffsetDateTime;

/**
 * @author daniel
 *
 */
public class AcreditacionPagoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6896761854027000589L;

	public AcreditacionPagoException(Integer anho, Integer mes, OffsetDateTime fechapago) {
		super("Cannot find AcreditacionPago " + anho + "/" + mes + "/" + fechapago);
	}

	public AcreditacionPagoException(Long acreditacionpagoId) {
		super("Cannot find AcreditacionPago " + acreditacionpagoId);
	}
}
