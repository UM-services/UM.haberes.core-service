/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class DependenciaException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7308393732617040302L;

	public DependenciaException(Integer dependenciaId) {
		super("Cannot find Dependencia " + dependenciaId);
	}

	public DependenciaException(Integer facultadId, Integer geograficaId) {
		super("Cannot find Dependencia " + facultadId + "/" + geograficaId);
	}

}
