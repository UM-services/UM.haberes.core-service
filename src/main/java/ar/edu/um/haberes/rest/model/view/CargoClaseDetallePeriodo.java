/**
 * 
 */
package ar.edu.um.haberes.rest.model.view;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import ar.edu.um.haberes.rest.model.Auditable;
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
@Table(name = "vw_cargo_clase_detalle_periodo")
@Immutable
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CargoClaseDetallePeriodo extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -2668207560384966175L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cargoClaseDetalleId;
	
	private Long legajoId;
	private Integer anho;
	private Integer mes;
	private Long cargoClaseId;
	private Integer dependenciaId;
	private Integer facultadId;
	private Integer geograficaId;
	private Integer horas;
	private BigDecimal valorHora;
	private Long cargoClasePeriodoId;
	private Byte liquidado;
	private Long periodo;
	
}
