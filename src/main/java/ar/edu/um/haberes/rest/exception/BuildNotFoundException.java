/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class BuildNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7816888198436311667L;

	public BuildNotFoundException() {
		super("Cannot find Build");
	}
}
