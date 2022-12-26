/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class AdicionalCursoTablaNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 9062897757174138602L;

	public AdicionalCursoTablaNotFoundException(Long adicionalCursoTablaId) {
		super(MessageFormat.format("Cannot find AdicionalCursoTabla {0}", adicionalCursoTablaId));
	}

	public AdicionalCursoTablaNotFoundException(Integer facultadId, Integer anho, Integer mes) {
		super(MessageFormat.format("Cannot find AdicionalCursoTabla {0}/{1}/{2}", facultadId, anho, mes));
	}

}
