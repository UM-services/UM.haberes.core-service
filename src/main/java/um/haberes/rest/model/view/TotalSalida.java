/**
 * 
 */
package um.haberes.rest.model.view;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Table(name = "vw_total_salida")
@Immutable
@NoArgsConstructor
@AllArgsConstructor
public class TotalSalida implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 3754746357993954302L;

	@Id
	private String uniqueId;

	private Integer anho;
	private Integer mes;
	private BigDecimal totalRemunerativo = BigDecimal.ZERO;
	private BigDecimal totalNoRemunerativo = BigDecimal.ZERO;
	private BigDecimal totalDeduccion = BigDecimal.ZERO;
	private BigDecimal totalNeto = BigDecimal.ZERO;

}
