/**
 * 
 */
package um.haberes.core.exception;

import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class CategoriaPeriodoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1933079115982958394L;

	public CategoriaPeriodoException(Integer categoriaId, Integer anho, Integer mes) {
		super(MessageFormat.format("Cannot find CategoriaPeriodo {0}/{1}/{2}", categoriaId, anho, mes));
	}

	public CategoriaPeriodoException(Long categoriaperiodoId) {
		super(MessageFormat.format("Cannot find CategoriaPeriodo {0}", categoriaperiodoId));
	}

}
