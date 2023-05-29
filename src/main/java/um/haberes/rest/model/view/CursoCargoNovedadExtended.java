/**
 * 
 */
package um.haberes.rest.model.view;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import org.hibernate.annotations.Immutable;

import um.haberes.rest.model.Auditable;
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
@Table(name = "vw_curso_cargo_novedad_extended", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "cursoId", "anho", "mes", "cargoTipoId", "legajoId" }) })
@Immutable
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CursoCargoNovedadExtended extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2199123592387555604L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cursoCargoNovedadId;

	private Long cursoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Integer cargoTipoId;
	private Long legajoId;
	private BigDecimal horasSemanales = new BigDecimal(0);
	private BigDecimal horasTotales = new BigDecimal(0);
	private Byte desarraigo = 0;
	private Byte alta = 0;
	private Byte baja = 0;
	private Byte cambio = 0;
	private String solicitud;
	private Byte autorizado = 0;
	private Byte rechazado = 0;
	private String respuesta;
	private Byte transferido = 0;
	private Byte aCargo = 0;
	private String apellido = "";
	private String nombre = "";

}
