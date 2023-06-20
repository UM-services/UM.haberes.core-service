/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class LegajoContabilidadException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1936202151163845889L;

	public LegajoContabilidadException() {
		super("Cannot find LegajoContabilidad");
	}

	public LegajoContabilidadException(Long legajoID, Integer anho, Integer mes) {
		super("Cannot find LegajoContabilidad " + legajoID + "/" + anho + "/" + mes);
	}

	public LegajoContabilidadException(Long legajocontabilidadID) {
		super("Cannot find LegajoContabilidad " + legajocontabilidadID);
	}
}
