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
public class LegajoCategoriaImputacion extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -2131284361342741528L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long legajoCategoriaImputacionId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Integer dependenciaId;
	private Integer facultadId;
	private Integer geograficaId;
	private Integer categoriaId;
	private BigDecimal cuentaSueldos;
	private BigDecimal basico = BigDecimal.ZERO;
	private BigDecimal antiguedad = BigDecimal.ZERO;
	private BigDecimal cuentaAportes;
	
	@OneToOne
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

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
	@JoinColumn(name = "categoriaId", insertable = false, updatable = false)
	private Categoria categoria;

}
