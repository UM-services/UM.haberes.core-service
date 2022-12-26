/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

import java.math.BigDecimal;
import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class PersonaNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8813165673257060305L;

	public PersonaNotFoundException() {
		super("Cannot find Persona");
	}

	public PersonaNotFoundException(Long legajoId) {
		super(MessageFormat.format("Cannot find Persona {0}", legajoId));
	}

	public PersonaNotFoundException(BigDecimal documento) {
		super(MessageFormat.format("Cannot find Persona {0}", documento));
	}

}
