/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CargoClaseImputacionNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -774259631502621009L;

	public CargoClaseImputacionNotFoundException(Long cargoclaseimputacionId) {
		super("Cannot find CargoClaseImputacion " + cargoclaseimputacionId);
	}

	public CargoClaseImputacionNotFoundException(Integer dependenciaId, Integer facultadId, Integer geograficaId,
			Long cargoclaseId) {
		super("Cannot find CargoClaseImputacion " + dependenciaId + "/" + facultadId + "/" + geograficaId + "/"
				+ cargoclaseId);
	}

}
