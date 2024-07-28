/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class AnotadorException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -132659883714362169L;

	public AnotadorException(Long anotadorId) {
		super("Cannot find Anotador " + anotadorId);
	}

}
