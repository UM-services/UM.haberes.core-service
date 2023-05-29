/**
 * 
 */
package um.haberes.rest.model.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;

import um.haberes.rest.model.Auditable;
import um.haberes.rest.model.Categoria;
import um.haberes.rest.model.Dependencia;
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
@Table(name = "vw_cargo_liquidacion_periodo")
@Immutable
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CargoLiquidacionPeriodo extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5516084407748915092L;

	@Id
	private Long cargoLiquidacionId;

	private Long legajoId;
	private Integer anho;
	private Integer mes;
	private Integer dependenciaId;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaDesde;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaHasta;

	private Integer categoriaId;
	private String categoriaNombre;
	private BigDecimal categoriaBasico;
	private Integer jornada = 0;
	private Integer presentismo = 0;
	private BigDecimal asignacionEspecialPermanente = BigDecimal.ZERO;
	private String situacion;
	private Long periodo;

	@OneToOne
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

	@OneToOne
	@JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
	private Dependencia dependencia;

	@OneToOne
	@JoinColumn(name = "categoriaId", insertable = false, updatable = false)
	private Categoria categoria;

}
