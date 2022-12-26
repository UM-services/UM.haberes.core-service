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
import lombok.ToString;

/**
 * @author daniel
 *
 */
@Entity
@EqualsAndHashCode(callSuper=false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CursoDesarraigo extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2495699452327497906L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cursoDesarraigoId;
	
	private Long legajoId;
	private Integer anho = 0;
	private Integer mes;
	private Long cursoId;
	private Integer geograficaId;
	private BigDecimal importe = BigDecimal.ZERO;
	private Integer version;
	
	@OneToOne
	@JoinColumn(name = "cursoId", insertable = false, updatable = false)
	private Curso curso;

	@OneToOne
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

	@OneToOne
	@JoinColumn(name = "geograficaId", insertable = false, updatable = false)
	private Geografica geografica;

}
