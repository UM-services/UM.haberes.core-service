/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class LegajoBancoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1824686590094373186L;

	public LegajoBancoNotFoundException(Long legajoId, Integer anho, Integer mes, String cbu) {
		super(MessageFormat.format("Cannot find LegajoBanco {0}/{1}/{2}/{3}", legajoId, anho, mes, cbu));
	}

	public LegajoBancoNotFoundException(Long legajobancoId) {
		super(MessageFormat.format("Cannot find LegajoBanco {0}", legajobancoId));
	}

	public LegajoBancoNotFoundException(Long legajoId, Integer anho, Integer mes) {
		super(MessageFormat.format("Cannot find LegajoBanco {0}/{1}/{2}", legajoId, anho, mes));
	}

}
