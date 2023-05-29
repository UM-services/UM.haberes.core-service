/**
 * 
 */
package um.haberes.rest.exception;

import java.time.OffsetDateTime;

/**
 * @author daniel
 *
 */
public class AcreditacionPagoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6896761854027000589L;

	public AcreditacionPagoNotFoundException(Integer anho, Integer mes, OffsetDateTime fechapago) {
		super("Cannot find AcreditacionPago " + anho + "/" + mes + "/" + fechapago);
	}

	public AcreditacionPagoNotFoundException(Long acreditacionpagoId) {
		super("Cannot find AcreditacionPago " + acreditacionpagoId);
	}
}
