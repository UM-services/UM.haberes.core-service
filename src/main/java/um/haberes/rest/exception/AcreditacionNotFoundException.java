/**
 * 
 */
package um.haberes.rest.exception;

import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class AcreditacionNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8252477181005475612L;

	public AcreditacionNotFoundException() {
		super("Could not find Acreditacion");
	}

	public AcreditacionNotFoundException(Integer anho, Integer mes) {
		super(MessageFormat.format("Cannot find Acreditacion {0}/{1}", mes, anho));
	}

	public AcreditacionNotFoundException(Long acreditacionId) {
		super("Could not find Acreditacion " + acreditacionId);
	}
}
