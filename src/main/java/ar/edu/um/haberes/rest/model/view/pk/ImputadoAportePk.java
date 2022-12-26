/**
 * 
 */
package ar.edu.um.haberes.rest.model.view.pk;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

/**
 * @author daniel
 *
 */
@Data
public class ImputadoAportePk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2879444463493290694L;
	
	private Integer anho;
	private Integer mes;
	private BigDecimal cuentaAportes;
}
