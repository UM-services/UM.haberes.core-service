/**
 * 
 */
package um.haberes.rest.model.view;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import org.hibernate.annotations.Immutable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Table(name = "vw_novedad_acumulado", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "legajoId", "anho", "mes", "codigoId" }) })
@Immutable
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class NovedadAcumulado implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 6309904105574012597L;

	@Id
	private String unified;
	
	private Long legajoId;
	private Integer anho;
	private Integer mes;
	private Integer codigoId;
	private BigDecimal importe;
	private Integer cantidad;

}
