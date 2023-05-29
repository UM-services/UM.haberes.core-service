/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CodigoGrupoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1871252958121853431L;

	public CodigoGrupoNotFoundException(Integer codigoId) {
		super("Cannot find CodigoGrupo " + codigoId);
	}

}
