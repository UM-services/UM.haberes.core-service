/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class NovedadNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2497109679875917076L;

	public NovedadNotFoundException(Long legajoId, Integer anho, Integer mes, Integer codigoId, Integer dependenciaId) {
		super("Cannot find Novedad " + legajoId + "/" + anho + "/" + mes + "/" + codigoId + "/" + dependenciaId);
	}

	public NovedadNotFoundException(Long novedadId) {
		super("Cannot find Novedad " + novedadId);
	}

}
