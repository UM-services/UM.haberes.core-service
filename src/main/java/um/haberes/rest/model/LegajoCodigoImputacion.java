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
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LegajoCodigoImputacion extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 6766005379281727954L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long legajoCodigoImputacionId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Integer dependenciaId;
	private Integer facultadId;
	private Integer geograficaId;
	private Integer codigoId;
	private BigDecimal cuentaSueldos;
	private BigDecimal importe = BigDecimal.ZERO;
	private BigDecimal cuentaAportes;
	
}
