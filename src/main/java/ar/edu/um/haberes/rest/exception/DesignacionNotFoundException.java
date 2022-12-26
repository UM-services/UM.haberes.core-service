/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class DesignacionNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4861586180029856359L;

	public DesignacionNotFoundException(Integer designaciontipoId, Integer cargotipoId, Byte anual, Byte semestral) {
		super("Cannot find Designacion " + designaciontipoId + "/" + cargotipoId + "/" + anual + "/" + semestral);
	}

}
