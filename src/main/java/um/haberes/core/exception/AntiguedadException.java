/**
 * 
 */
package um.haberes.core.exception;

/**
 * @author daniel
 *
 */
public class AntiguedadException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5411761400810512392L;
	
	public AntiguedadException(Long legajoID, Integer anho, Integer mes) {
		super("Cannot find Antiguedad " + legajoID + "/" + anho + "/" + mes);
	}

	public AntiguedadException(Long antiguedadId) {
		super("Cannot find Antiguedad " + antiguedadId);
	}

}
