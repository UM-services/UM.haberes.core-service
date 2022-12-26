/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class ContactoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5746364801479133582L;

	public ContactoNotFoundException(Long legajoId) {
		super("Cannot find Contacto " + legajoId);
	}

}
