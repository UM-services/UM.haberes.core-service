/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class ControlException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4419301670546270393L;

	public ControlException(Integer anho, Integer mes) {
		super("Cannot find Control " + anho + "/" + mes);
	}

	public ControlException(Long controlId) {
		super("Cannot find Control " + controlId);
	}

}
