/**
 * 
 */
package um.haberes.core.exception;

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
		super("Cannot find ControlEntity " + anho + "/" + mes);
	}

	public ControlException(Long controlId) {
		super("Cannot find ControlEntity " + controlId);
	}

}
