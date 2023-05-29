/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class LegajoContabilidadNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1936202151163845889L;

	public LegajoContabilidadNotFoundException() {
		super("Cannot find LegajoContabilidad");
	}

	public LegajoContabilidadNotFoundException(Long legajoID, Integer anho, Integer mes) {
		super("Cannot find LegajoContabilidad " + legajoID + "/" + anho + "/" + mes);
	}

	public LegajoContabilidadNotFoundException(Long legajocontabilidadID) {
		super("Cannot find LegajoContabilidad " + legajocontabilidadID);
	}
}
