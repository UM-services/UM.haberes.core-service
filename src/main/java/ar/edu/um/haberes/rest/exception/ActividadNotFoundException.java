/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class ActividadNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5665372249169178950L;

	public ActividadNotFoundException(Long legajoID, Integer anho, Integer mes) {
		super("Cannot find Actividad " + legajoID + "/" + anho + "/" + mes);
	}

	public ActividadNotFoundException(Long actividadID) {
		super("Cannot find Actividad " + actividadID);
	}
}
