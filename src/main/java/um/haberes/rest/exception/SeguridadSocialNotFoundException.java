/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class SeguridadSocialNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4209734848990855225L;

	public SeguridadSocialNotFoundException(Integer anho, Integer mes) {
		super("Cannot found FF931 " + anho + "/" + mes);
	}

	public SeguridadSocialNotFoundException(Long seguridadSocialId) {
		super("Cannot found FF931 " + seguridadSocialId);
	}

}
