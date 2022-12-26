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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "legajoId", "anho", "mes" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Liquidacion extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1304354815796829121L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long liquidacionId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaLiquidacion;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaAcreditacion;
	
	private Integer dependenciaId;
	private String salida;
	private BigDecimal totalRemunerativo = BigDecimal.ZERO;
	private BigDecimal totalNoRemunerativo = BigDecimal.ZERO;
	private BigDecimal totalDeduccion = BigDecimal.ZERO;
	private BigDecimal totalNeto = BigDecimal.ZERO;
	private Byte bloqueado = 0;
	private Integer estado = 0;
	private String liquida = "";

	@OneToOne
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

	@OneToOne
	@JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
	private Dependencia dependencia;

}
