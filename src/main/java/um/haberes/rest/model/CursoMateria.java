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
import um.haberes.rest.kotlin.model.Curso;

/**
 * @author daniel
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "cursoId", "facultadId",
		"geograficaId", "planId", "carreraId", "materiaId" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursoMateria extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -947881750890493221L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cursoMateriaId;

	private Long cursoId;
	private Integer facultadId;
	private Integer geograficaId;
	private Integer planId;
	private Integer carreraId;
	private String materiaId;
	private BigDecimal proporcion = BigDecimal.ZERO;

	@OneToOne
	@JoinColumn(name = "cursoId", insertable = false, updatable = false)
	private Curso curso;

	@OneToOne
	@JoinColumn(name = "facultadId", insertable = false, updatable = false)
	private Facultad facultad;

	@OneToOne
	@JoinColumn(name = "geograficaId", insertable = false, updatable = false)
	private Geografica geografica;
	
}
