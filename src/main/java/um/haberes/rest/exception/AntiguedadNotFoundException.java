/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class AntiguedadNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5411761400810512392L;
	
	public AntiguedadNotFoundException(Long legajoID, Integer anho, Integer mes) {
		super("Cannot find Antiguedad " + legajoID + "/" + anho + "/" + mes);
	}

	public AntiguedadNotFoundException(Long antiguedadId) {
		super("Cannot find Antiguedad " + antiguedadId);
	}

}
