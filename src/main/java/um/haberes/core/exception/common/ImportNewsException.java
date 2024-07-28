/**
 * 
 */
package um.haberes.core.exception.common;

import java.text.MessageFormat;

/**
 * @author daniel
 *
 */
public class ImportNewsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4736204717350434117L;

	public ImportNewsException(Long legajoId, Integer anho, Integer mes, Integer codigoId, Integer dependenciaId) {
		super(MessageFormat.format(
				"Error importación -> legajo: {0} - anho: {1} - mes: {2} - codigo: {3} - dependencia: {4}", legajoId,
				anho, mes, codigoId, dependenciaId));
	}

}
