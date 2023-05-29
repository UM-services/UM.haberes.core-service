/**
 * 
 */
package um.haberes.rest.exception;

import java.math.BigDecimal;

/**
 * @author daniel
 *
 */
public class DesignacionTipoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8759168362280238248L;

	public DesignacionTipoNotFoundException(BigDecimal horassemanales) {
		super("Cannot find DesignacionTipo " + horassemanales);
	}

	public DesignacionTipoNotFoundException(Integer designaciontipoId) {
		super("Cannot find DesignacionTipo " + designaciontipoId);
	}

}
