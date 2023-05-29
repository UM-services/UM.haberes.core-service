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
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = { "dependenciaId", "facultadId", "geograficaId", "codigoId" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CodigoImputacion extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -24094967294542981L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigoImputacionId;

	private Integer dependenciaId;
	private Integer facultadId;
	private Integer geograficaId;
	private Integer codigoId;
	private BigDecimal cuentaSueldosDocente;
	private BigDecimal cuentaAportesDocente;
	private BigDecimal cuentaSueldosNoDocente;
	private BigDecimal cuentaAportesNoDocente;

	public String key() {
		return dependenciaId + "." + facultadId + "." + geograficaId + "." + codigoId;
	}

}
