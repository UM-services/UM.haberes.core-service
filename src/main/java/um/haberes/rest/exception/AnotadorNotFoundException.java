/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class AnotadorNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -132659883714362169L;

	public AnotadorNotFoundException(Long anotadorId) {
		super("Cannot find Anotador " + anotadorId);
	}

}
