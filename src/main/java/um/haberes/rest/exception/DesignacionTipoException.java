/**
 * 
 */
package um.haberes.rest.exception;

import java.math.BigDecimal;

/**
 * @author daniel
 *
 */
public class DesignacionTipoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8759168362280238248L;

	public DesignacionTipoException(BigDecimal horassemanales) {
		super("Cannot find DesignacionTipo " + horassemanales);
	}

	public DesignacionTipoException(Integer designaciontipoId) {
		super("Cannot find DesignacionTipo " + designaciontipoId);
	}

}
