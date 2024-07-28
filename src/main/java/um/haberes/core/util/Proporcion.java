/**
 * 
 */
package um.haberes.core.util;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Proporcion implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 4982292466649666262L;

	private Integer dependenciaId;
	private Integer facultadId;
	private Integer geograficaId;
	private Byte docente;
	private BigDecimal total;
	private BigDecimal porcentaje;
	
	public String key() {
		return dependenciaId + "." + facultadId + "." + geograficaId + "." + docente;
	}

}
