/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class CodigoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4891436742167836975L;

	public CodigoException(Integer codigoId) {
		super("Cannot find Codigo " + codigoId);
	}

	public CodigoException() {
		super("Cannot find Codigo");
	}

}
