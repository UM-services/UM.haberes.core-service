/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class LiquidacionNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8452069536111975095L;

	public LiquidacionNotFoundException(Long liquidacionId) {
		super("Cannot find Liquidacion " + liquidacionId);
	}

	public LiquidacionNotFoundException(Long legajoId, Integer anho, Integer mes) {
		super("Cannot find Liquidacion " + legajoId + "/" + anho + "/" + mes);
	}

}
