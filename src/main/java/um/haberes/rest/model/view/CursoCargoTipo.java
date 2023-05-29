/**
 * 
 */
package um.haberes.rest.model.view;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import um.haberes.rest.model.Auditable;
import um.haberes.rest.model.CargoTipo;
import um.haberes.rest.model.Categoria;
import um.haberes.rest.model.Curso;
import um.haberes.rest.model.DesignacionTipo;
import um.haberes.rest.model.Persona;
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
@Table(name = "vw_curso_cargo_tipo")
@Immutable
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CursoCargoTipo extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2452577458827992061L;

	@Id
	private Long cursoCargoId;

	private Long cursoId;
	private Integer anho;
	private Integer mes;
	private Integer cargoTipoId;
	private Long legajoId;
	private BigDecimal horasSemanales;
	private BigDecimal horasTotales;
	private Integer designacionTipoId;
	private Integer categoriaId;
	private Byte desarraigo;
	private Long cursoCargoNovedadId;
	private Byte aCargo;

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
