/**
 * 
 */
package um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class ItemNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9164260291678520296L;

	public ItemNotFoundException(Long legajoId, Integer anho, Integer mes, Integer codigoId) {
		super("Cannot find Item " + legajoId + "/" + anho + "/" + mes + "/" + codigoId);
	}

	public ItemNotFoundException(Long itemId) {
		super("Cannot find Item " + itemId);
	}

}
