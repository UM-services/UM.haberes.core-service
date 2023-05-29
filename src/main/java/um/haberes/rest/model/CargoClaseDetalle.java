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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "cargoClasePeriodoId", "anho", "mes" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoClaseDetalle extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1538133904656877507L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cargoClaseDetalleId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Long cargoClaseId;
	private Integer dependenciaId;
	private Integer facultadId;
	private Integer geograficaId;
	private Integer horas = 0;
	private BigDecimal valorHora = BigDecimal.ZERO;
	private Long cargoClasePeriodoId;
	private Byte liquidado = 0;

	@OneToOne
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

	@OneToOne
	@JoinColumn(name = "cargoClaseId", insertable = false, updatable = false)
	private CargoClase cargoClase;

	@OneToOne
	@JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
	private Dependencia dependencia;

	@OneToOne
	@JoinColumn(name = "facultadId", insertable = false, updatable = false)
	private Facultad facultad;

	@OneToOne
	@JoinColumn(name = "geograficaId", insertable = false, updatable = false)
	private Geografica geografica;

	@OneToOne
	@JoinColumn(name = "cargoClasePeriodoId", insertable = false, updatable = false)
	private CargoClasePeriodo cargoClasePeriodo;

}
