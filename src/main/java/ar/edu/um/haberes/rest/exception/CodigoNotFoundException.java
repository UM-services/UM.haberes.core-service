/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CodigoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4891436742167836975L;

	public CodigoNotFoundException(Integer codigoId) {
		super("Cannot find Codigo " + codigoId);
	}

	public CodigoNotFoundException() {
		super("Cannot find Codigo");
	}

}
