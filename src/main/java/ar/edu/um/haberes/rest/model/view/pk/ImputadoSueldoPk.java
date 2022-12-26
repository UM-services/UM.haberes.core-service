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
public class ImputadoSueldoPk implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -561392596603847423L;

	private Integer anho;
	private Integer mes;
	private BigDecimal cuentaSueldos;
}
