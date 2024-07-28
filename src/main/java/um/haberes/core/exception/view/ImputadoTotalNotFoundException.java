/**
 * 
 */
package um.haberes.core.exception.view;

/**
 * @author daniel
 *
 */
public class ImputadoTotalNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6196543332514799719L;

	public ImputadoTotalNotFoundException (Integer anho, Integer mes) {
		super("Cannot find ImputadoTotal " + mes + "/" + anho);
	}
}
