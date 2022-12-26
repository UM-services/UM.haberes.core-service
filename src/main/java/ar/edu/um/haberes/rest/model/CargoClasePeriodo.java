/**
 * 
 */
package ar.edu.um.haberes.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

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
public class CargoClasePeriodo extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 3290045988457434072L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cargoClasePeriodoId;

	private Long legajoId;
	private Long cargoClaseId;
	private Integer dependenciaId;
	private Integer facultadId;
	private Integer geograficaId;
	private Long periodoDesde;
	private Long periodoHasta;
	private Integer horas = 0;
	private BigDecimal valorHora = BigDecimal.ZERO;
	private String descripcion;

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

}
