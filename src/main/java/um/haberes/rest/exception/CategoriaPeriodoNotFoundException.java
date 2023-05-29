/**
 * 
 */
package um.haberes.rest.exception;

import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class CategoriaPeriodoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1933079115982958394L;

	public CategoriaPeriodoNotFoundException(Integer categoriaId, Integer anho, Integer mes) {
		super(MessageFormat.format("Cannot find CategoriaPeriodo {0}/{1}/{2}", categoriaId, anho, mes));
	}

	public CategoriaPeriodoNotFoundException(Long categoriaperiodoId) {
		super(MessageFormat.format("Cannot find CategoriaPeriodo {0}", categoriaperiodoId));
	}

}
