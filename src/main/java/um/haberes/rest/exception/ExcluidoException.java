/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class ExcluidoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8235852665295644400L;

	public ExcluidoException(Long legajoId, Integer anho, Integer mes) {
		super("Cannot find Excluido " + legajoId + "/" + anho + "/" + mes);
	}

	public ExcluidoException(Long excluidoId) {
		super("Cannot find Excluido " + excluidoId);
	}

}
