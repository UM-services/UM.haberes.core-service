/**
 * 
 */
package um.haberes.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author daniel
 *
 */
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DesignacionTipo extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 7761936207749645488L;

	@Id
	private Integer designacionTipoId = 0;

	private String nombre = "";
	private BigDecimal horasSemanales = BigDecimal.ZERO;
	private BigDecimal horasTotales = BigDecimal.ZERO;
	private Integer simples = 0;

}
