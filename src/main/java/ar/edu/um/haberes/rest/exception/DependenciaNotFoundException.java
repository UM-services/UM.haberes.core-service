/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class DependenciaNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7308393732617040302L;

	public DependenciaNotFoundException(Integer dependenciaId) {
		super("Cannot find Dependencia " + dependenciaId);
	}

	public DependenciaNotFoundException(Integer facultadId, Integer geograficaId) {
		super("Cannot find Dependencia " + facultadId + "/" + geograficaId);
	}

}
