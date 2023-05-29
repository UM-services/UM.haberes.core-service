/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class LetraNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3142576371909626133L;

	public LetraNotFoundException(Long legajoId, Integer anho, Integer mes) {
		super("Cannot find Letra " + legajoId + "/" + anho + "/" + mes);
	}

	public LetraNotFoundException(Long letraId) {
		super("Cannot find Letra " + letraId);
	}

}
