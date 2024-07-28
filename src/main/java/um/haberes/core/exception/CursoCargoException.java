/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class CursoCargoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7374124802111474982L;

	public CursoCargoException(Long cursoId, Integer anho, Integer mes, Integer cargotipoId, Long legajoId) {
		super("Cannot find CursoCargo " + cursoId + "/" + anho + "/" + mes + "/" + cargotipoId + "/" + legajoId);
	}

	public CursoCargoException(Long cursoId, Integer anho, Integer mes, Long legajoId) {
		super("Cannot find CursoCargo " + cursoId + "/" + anho + "/" + mes + "/" + "/" + legajoId);
	}

	public CursoCargoException(Long cursocargoId) {
		super("Cannot find CursoCargo " + cursocargoId);
	}

}
