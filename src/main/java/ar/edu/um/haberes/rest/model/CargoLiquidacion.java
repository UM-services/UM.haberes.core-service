/**
 * 
 */
package ar.edu.um.haberes.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class CargoLiquidacion extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2029847194729942201L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	private String categoriaNombre = "";
	private BigDecimal categoriaBasico = BigDecimal.ZERO;
	private BigDecimal horasJornada = BigDecimal.ZERO;
	private Integer jornada = 0;
	private Integer presentismo = 0;
	private String situacion;

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
