/**
 * 
 */
package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.exception;

/**
 * @author daniel
 *
 */
public class CargoLiquidacionException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6041246655118136892L;

	public CargoLiquidacionException(Long legajoId, Integer anho, Integer mes, Integer categoriaId) {
		super("Cannot find CargoEntity " + legajoId + "/" + anho + "/" + mes + "/" + categoriaId);
	}

	public CargoLiquidacionException(Long cargoId) {
		super("Cannot find CargoEntity " + cargoId);
	}

}
