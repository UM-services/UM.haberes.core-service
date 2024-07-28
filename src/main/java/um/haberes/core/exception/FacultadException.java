/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class FacultadException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6076787277820964417L;

	public FacultadException(Integer facultadId) {
		super("Cannot find Facultad " + facultadId);
	}

}
