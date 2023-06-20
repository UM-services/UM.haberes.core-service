/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CodigoGrupoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1871252958121853431L;

	public CodigoGrupoException(Integer codigoId) {
		super("Cannot find CodigoGrupo " + codigoId);
	}

}
