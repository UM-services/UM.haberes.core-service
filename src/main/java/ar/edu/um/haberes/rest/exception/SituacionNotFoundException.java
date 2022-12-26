/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class SituacionNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2819008821750644530L;
	
	public SituacionNotFoundException(Integer situacionID) {
		super("Cannot find Situacion " + situacionID);
	}

}
