/**
 * 
 */
package um.haberes.rest.exception;

import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class AdicionalCursoTablaException extends RuntimeException {

	private static final long serialVersionUID = 9062897757174138602L;

	public AdicionalCursoTablaException(Long adicionalCursoTablaId) {
		super(MessageFormat.format("Cannot find AdicionalCursoTabla {0}", adicionalCursoTablaId));
	}

	public AdicionalCursoTablaException(Integer facultadId, Integer anho, Integer mes) {
		super(MessageFormat.format("Cannot find AdicionalCursoTabla {0}/{1}/{2}", facultadId, anho, mes));
	}

	public AdicionalCursoTablaException(Integer facultadId, Integer geograficaId, Integer anho, Integer mes) {
		super(MessageFormat.format("Cannot find AdicionalCursoTabla {0}/{1}/{2}/{3}", facultadId, geograficaId, anho, mes));
	}

}
