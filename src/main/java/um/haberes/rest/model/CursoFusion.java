/**
 * 
 */
package um.haberes.rest.model;

import java.io.Serializable;

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
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CursoFusion extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 6505435494868266556L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cursoFusionId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Integer facultadId;
	private Integer geograficaId;
	private Integer cargoTipoId;
	private Integer designacionTipoId;
	private Byte anual;
	private Integer categoriaId;

	@OneToOne(optional = true)
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

	@OneToOne(optional = true)
	@JoinColumn(name = "facultadId", insertable = false, updatable = false)
	private Facultad facultad;

	@OneToOne(optional = true)
	@JoinColumn(name = "geograficaId", insertable = false, updatable = false)
	private Geografica geografica;

	@OneToOne(optional = true)
	@JoinColumn(name = "cargoTipoId", insertable = false, updatable = false)
	private CargoTipo cargoTipo;

	@OneToOne(optional = true)
	@JoinColumn(name = "designacionTipoId", insertable = false, updatable = false)
	private CargoTipo designacionTipo;

	@OneToOne(optional = true)
	@JoinColumn(name = "categoriaId", insertable = false, updatable = false)
	private Categoria categoria;

}
