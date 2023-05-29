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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "cursoId", "anho", "mes", "cargoTipoId", "legajoId" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursoCargoNovedad extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 7301936091573172476L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cursoCargoNovedadId;

	private Long cursoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Integer cargoTipoId;
	private Long legajoId;
	private BigDecimal horasSemanales = BigDecimal.ZERO;
	private BigDecimal horasTotales = BigDecimal.ZERO;
	private Byte desarraigo = 0;
	private Byte alta = 0;
	private Byte baja = 0;
	private Byte cambio = 0;
	private String solicitud;
	private Byte autorizado = 0;
	private Byte rechazado = 0;
	private String respuesta;
	private Byte transferido = 0;

	@OneToOne
	@JoinColumn(name = "cursoId", insertable = false, updatable = false)
	private Curso curso;

	@OneToOne
	@JoinColumn(name = "cargoTipoId", insertable = false, updatable = false)
	private CargoTipo cargoTipo;

	@OneToOne
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

}
