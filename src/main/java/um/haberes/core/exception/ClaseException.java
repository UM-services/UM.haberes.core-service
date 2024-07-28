/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class ClaseException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7384247627894476254L;

	public ClaseException() {
		super("Could not find Clase ");
	}

	public ClaseException(Integer claseID) {
		super("Could not find Clase " + claseID);
	}
}
