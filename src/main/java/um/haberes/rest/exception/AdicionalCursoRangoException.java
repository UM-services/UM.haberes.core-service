/**
 * 
 */
package um.haberes.rest.exception;

import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class AdicionalCursoRangoException extends RuntimeException {

	private static final long serialVersionUID = -2138729640886420324L;

	public AdicionalCursoRangoException(Long adicionalCursoRangoId) {
		super(MessageFormat.format("Cannot find AdicionalCursoRango {}", adicionalCursoRangoId));
	}

}
