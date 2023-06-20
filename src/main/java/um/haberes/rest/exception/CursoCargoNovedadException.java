/**
 * 
 */
package um.haberes.rest.exception;

import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class CursoCargoNovedadException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1008177911075562987L;

	public CursoCargoNovedadException(Long cursoId, Integer anho, Integer mes, Integer cargoTipoId,
									  Long legajoId) {
		super(MessageFormat.format("Cannot found CursoCargoNovedad {0}/{1}/{2}/{3}/{4}", cursoId, anho, mes,
				cargoTipoId, legajoId));
	}

	public CursoCargoNovedadException(Long cursocargonovedadId) {
		super("Cannot found CursoCargoNovedad " + cursocargonovedadId);
	}

	public CursoCargoNovedadException(Long legajoId, Long cursoId, Integer anho, Integer mes) {
		super(MessageFormat.format("Cannot found CursoCargoNovedad {0}/{1}/{2}/{3}", legajoId, cursoId, anho, mes));
	}

}
