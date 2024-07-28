/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class SeguridadSocialException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4209734848990855225L;

	public SeguridadSocialException(Integer anho, Integer mes) {
		super("Cannot found FF931 " + anho + "/" + mes);
	}

	public SeguridadSocialException(Long seguridadSocialId) {
		super("Cannot found FF931 " + seguridadSocialId);
	}

}
