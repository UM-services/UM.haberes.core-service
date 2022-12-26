/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class FacultadNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6076787277820964417L;

	public FacultadNotFoundException(Integer facultadId) {
		super("Cannot find Facultad " + facultadId);
	}

}
