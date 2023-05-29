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
import lombok.ToString;

/**
 * @author daniel
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "cursoId", "anho", "mes", "cargoTipoId", "legajoId" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CursoCargo extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 3440583907430980457L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cursoCargoId = null;

	private Long cursoId = null;
	private Integer anho = 0;
	private Integer mes = 0;
	private Integer cargoTipoId = null;
	private Long legajoId = null;
	private BigDecimal horasSemanales = BigDecimal.ZERO;
	private BigDecimal horasTotales = BigDecimal.ZERO;
	private Integer designacionTipoId = null;
	private Integer categoriaId = null;
	private Byte desarraigo = 0;
	private Long cursoCargoNovedadId = null;

	@OneToOne
	@JoinColumn(name = "cursoId", insertable = false, updatable = false)
	private Curso curso;

	@OneToOne
	@JoinColumn(name = "cargoTipoId", insertable = false, updatable = false)
	private CargoTipo cargoTipo;

	@OneToOne
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

	@OneToOne
	@JoinColumn(name = "designacionTipoId", insertable = false, updatable = false)
	private DesignacionTipo designacionTipo;

	@OneToOne
	@JoinColumn(name = "categoriaId", insertable = false, updatable = false)
	private Categoria categoria;

}
