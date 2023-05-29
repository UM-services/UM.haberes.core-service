/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CodigoImputacionNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1831995683375064385L;

	public CodigoImputacionNotFoundException(Long codigoimputacionId) {
		super("Cannot find CodigoImputacion " + codigoimputacionId);
	}

	public CodigoImputacionNotFoundException(Integer dependenciaId, Integer facultadId, Integer geograficaId,
			Integer codigoId) {
		super("Cannot find CodigoImputacion " + dependenciaId + "/" + facultadId + "/" + geograficaId + "/" + codigoId);
	}

}
