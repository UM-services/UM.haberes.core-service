/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class BuildException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7816888198436311667L;

	public BuildException() {
		super("Cannot find Build");
	}
}
