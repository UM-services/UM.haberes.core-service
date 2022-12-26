/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class ExcluidoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8235852665295644400L;

	public ExcluidoNotFoundException(Long legajoId, Integer anho, Integer mes) {
		super("Cannot find Excluido " + legajoId + "/" + anho + "/" + mes);
	}

	public ExcluidoNotFoundException(Long excluidoId) {
		super("Cannot find Excluido " + excluidoId);
	}

}
