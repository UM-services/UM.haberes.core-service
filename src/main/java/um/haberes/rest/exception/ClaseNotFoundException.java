/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class ClaseNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7384247627894476254L;

	public ClaseNotFoundException() {
		super("Could not find Clase ");
	}

	public ClaseNotFoundException(Integer claseID) {
		super("Could not find Clase " + claseID);
	}
}
