/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class LegajoControlNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1297421996138023694L;

	public LegajoControlNotFoundException(Long legajoId, Integer anho, Integer mes) {
		super(String.format("Cannot find LegajoControl %d/%d/%d", legajoId, anho, mes));
	}

	public LegajoControlNotFoundException(Long legajocontrolId) {
		super(String.format("Cannot find LegajoControl %d", legajocontrolId));
	}

}
