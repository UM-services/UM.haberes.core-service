/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class LiquidacionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8452069536111975095L;

	public LiquidacionException(Long liquidacionId) {
		super("Cannot find Liquidacion " + liquidacionId);
	}

	public LiquidacionException(Long legajoId, Integer anho, Integer mes) {
		super("Cannot find Liquidacion " + legajoId + "/" + anho + "/" + mes);
	}

}
