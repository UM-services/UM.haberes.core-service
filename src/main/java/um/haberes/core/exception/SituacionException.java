/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class SituacionException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2819008821750644530L;
	
	public SituacionException(Integer situacionID) {
		super("Cannot find Situacion " + situacionID);
	}

}
