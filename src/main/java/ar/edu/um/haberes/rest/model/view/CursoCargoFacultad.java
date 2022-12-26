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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import ar.edu.um.haberes.rest.model.CargoTipo;
import ar.edu.um.haberes.rest.model.Categoria;
import ar.edu.um.haberes.rest.model.Curso;
import ar.edu.um.haberes.rest.model.DesignacionTipo;
import ar.edu.um.haberes.rest.model.Persona;
import lombok.Data;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Immutable
@Table(name = "vw_curso_cargo_facultad")
public class CursoCargoFacultad implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 8921314178084971197L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private Integer facultadId;
	private Integer geograficaId;
	private Byte anual;
	private Byte semestre1;
	private Byte semestre2;

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
