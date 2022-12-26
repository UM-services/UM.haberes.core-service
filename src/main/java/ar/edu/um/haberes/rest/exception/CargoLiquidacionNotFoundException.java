/**
 * 
 */
package ar.edu.um.haberes.rest.exception;

/**
 * @author daniel
 *
 */
public class CargoLiquidacionNotFoundException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6041246655118136892L;

	public CargoLiquidacionNotFoundException(Long legajoId, Integer anho, Integer mes, Integer categoriaId) {
		super("Cannot find Cargo " + legajoId + "/" + anho + "/" + mes + "/" + categoriaId);
	}

	public CargoLiquidacionNotFoundException(Long cargoId) {
		super("Cannot find Cargo " + cargoId);
	}

}
