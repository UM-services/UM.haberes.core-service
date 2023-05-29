/**
 * 
 */
package um.haberes.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author daniel
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "legajoId", "anho", "mes" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LegajoContabilidad extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8841534093541924719L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long legajoContabilidadId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Byte diferencia = 0;
	private BigDecimal remunerativo = BigDecimal.ZERO;
	private BigDecimal noRemunerativo = BigDecimal.ZERO;

}
