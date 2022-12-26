/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class AntiguedadLimiteNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 315932932978600412L;

	public AntiguedadLimiteNotFoundException(Integer meses_docentes) {
		super("Cannot find AntiguedadLimite " + meses_docentes);
	}

}
