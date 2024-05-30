/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class AntiguedadLimiteException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 315932932978600412L;

	public AntiguedadLimiteException(Integer meses_docentes) {
		super("Cannot find AntiguedadLimite " + meses_docentes);
	}

}
