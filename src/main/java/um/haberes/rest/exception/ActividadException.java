/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class ActividadException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5665372249169178950L;

	public ActividadException(Long legajoId, Integer anho, Integer mes) {
		super("Cannot find Actividad " + legajoId + "/" + anho + "/" + mes);
	}

	public ActividadException(Long actividadId) {
		super("Cannot find Actividad " + actividadId);
	}
}
