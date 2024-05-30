/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CargoClaseImputacionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -774259631502621009L;

	public CargoClaseImputacionException(Long cargoclaseimputacionId) {
		super("Cannot find CargoClaseImputacion " + cargoclaseimputacionId);
	}

	public CargoClaseImputacionException(Integer dependenciaId, Integer facultadId, Integer geograficaId,
										 Long cargoclaseId) {
		super("Cannot find CargoClaseImputacion " + dependenciaId + "/" + facultadId + "/" + geograficaId + "/"
				+ cargoclaseId);
	}

}
