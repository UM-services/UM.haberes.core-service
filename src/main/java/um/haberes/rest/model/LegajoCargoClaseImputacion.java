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
public class LegajoCargoClaseImputacion extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4462866740002625960L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long legajoCargoClaseImputacionId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Integer dependenciaId;
	private Integer facultadId;
	private Integer geograficaId;
	private Long cargoClaseId;
	private BigDecimal cuentaSueldos;
	private BigDecimal basico = BigDecimal.ZERO;
	private BigDecimal antiguedad = BigDecimal.ZERO;
	private BigDecimal cuentaAportes;

}
