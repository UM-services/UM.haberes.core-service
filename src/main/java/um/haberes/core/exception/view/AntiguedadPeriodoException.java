/**
 * 
 */
package um.haberes.core.exception.view;

/**
 * @author daniel
 *
 */
public class AntiguedadPeriodoException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8624581443587541916L;

	public AntiguedadPeriodoException(Long legajoId, Integer anho, Integer mes) {
		super("Cannot find AntiguedadPeriodo " + legajoId + "/" + anho + "/" + mes);
	}

}
