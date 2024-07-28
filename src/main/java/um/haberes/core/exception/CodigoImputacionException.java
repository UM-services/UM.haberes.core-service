/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class CodigoImputacionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1831995683375064385L;

	public CodigoImputacionException(Long codigoimputacionId) {
		super("Cannot find CodigoImputacion " + codigoimputacionId);
	}

	public CodigoImputacionException(Integer dependenciaId, Integer facultadId, Integer geograficaId,
									 Integer codigoId) {
		super("Cannot find CodigoImputacion " + dependenciaId + "/" + facultadId + "/" + geograficaId + "/" + codigoId);
	}

}
