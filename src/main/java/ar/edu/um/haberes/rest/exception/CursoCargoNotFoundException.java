/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CursoCargoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7374124802111474982L;

	public CursoCargoNotFoundException(Long cursoId, Integer anho, Integer mes, Integer cargotipoId, Long legajoId) {
		super("Cannot find CursoCargo " + cursoId + "/" + anho + "/" + mes + "/" + cargotipoId + "/" + legajoId);
	}

	public CursoCargoNotFoundException(Long cursoId, Integer anho, Integer mes, Long legajoId) {
		super("Cannot find CursoCargo " + cursoId + "/" + anho + "/" + mes + "/" + "/" + legajoId);
	}

	public CursoCargoNotFoundException(Long cursocargoId) {
		super("Cannot find CursoCargo " + cursocargoId);
	}

}
