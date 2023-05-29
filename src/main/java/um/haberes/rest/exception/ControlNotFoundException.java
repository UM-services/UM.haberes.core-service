/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class ControlNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4419301670546270393L;

	public ControlNotFoundException(Integer anho, Integer mes) {
		super("Cannot find Control " + anho + "/" + mes);
	}

	public ControlNotFoundException(Long controlId) {
		super("Cannot find Control " + controlId);
	}

}
