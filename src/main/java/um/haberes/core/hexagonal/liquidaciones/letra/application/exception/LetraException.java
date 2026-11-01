/**
 * 
 */
package um.haberes.core.hexagonal.liquidaciones.letra.application.exception;

/**
 * @author daniel
 *
 */
public class LetraException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3142576371909626133L;

	public LetraException(Long legajoId, Integer anho, Integer mes) {
		super("Cannot find LetraEntity " + legajoId + "/" + anho + "/" + mes);
	}

	public LetraException(Long letraId) {
		super("Cannot find LetraEntity " + letraId);
	}

}
