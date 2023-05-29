/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class LegajoAcreditadoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8020674878590387394L;

	public LegajoAcreditadoNotFoundException(Long legajoacreditadoID) {
		super("Cannot find LegajoAcreditado " + legajoacreditadoID);
	}

	public LegajoAcreditadoNotFoundException(Long legajoID, Integer anho, Integer mes) {
		super("Cannot find LegajoAcreditado " + legajoID + "/" + anho + "/" + mes);
	}
}
