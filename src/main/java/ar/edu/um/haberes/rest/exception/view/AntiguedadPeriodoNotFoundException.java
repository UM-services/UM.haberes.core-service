/**
 * 
 */
package ar.edu.um.haberes.rest.exception.view;

/**
 * @author daniel
 *
 */
public class AntiguedadPeriodoNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8624581443587541916L;

	public AntiguedadPeriodoNotFoundException(Long legajoId, Integer anho, Integer mes) {
		super("Cannot find AntiguedadPeriodo " + legajoId + "/" + anho + "/" + mes);
	}

}
