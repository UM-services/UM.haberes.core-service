/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class LegajoControlException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1297421996138023694L;

	public LegajoControlException(Long legajoId, Integer anho, Integer mes) {
		super(String.format("Cannot find LegajoControl %d/%d/%d", legajoId, anho, mes));
	}

	public LegajoControlException(Long legajocontrolId) {
		super(String.format("Cannot find LegajoControl %d", legajocontrolId));
	}

}
