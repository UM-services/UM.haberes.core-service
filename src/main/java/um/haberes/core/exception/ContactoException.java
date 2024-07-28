/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class ContactoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5746364801479133582L;

	public ContactoException(Long legajoId) {
		super("Cannot find Contacto " + legajoId);
	}

}
