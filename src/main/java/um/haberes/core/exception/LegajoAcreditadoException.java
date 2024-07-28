/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class LegajoAcreditadoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8020674878590387394L;

	public LegajoAcreditadoException(Long legajoacreditadoID) {
		super("Cannot find LegajoAcreditado " + legajoacreditadoID);
	}

	public LegajoAcreditadoException(Long legajoID, Integer anho, Integer mes) {
		super("Cannot find LegajoAcreditado " + legajoID + "/" + anho + "/" + mes);
	}
}
