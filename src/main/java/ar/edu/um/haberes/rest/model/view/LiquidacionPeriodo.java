/**
 * 
 */
package ar.edu.um.haberes.rest.model.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import com.fasterxml.jackson.annotation.JsonFormat;

import ar.edu.um.haberes.rest.model.Auditable;
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
@Table(name = "vw_liquidacion_periodo")
@Immutable
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class LiquidacionPeriodo extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -8244392024318742826L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long liquidacionId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaLiquidacion;
	
	private Integer dependenciaId;
	private String salida;
	private BigDecimal totalRemunerativo = BigDecimal.ZERO;
	private BigDecimal totalNoRemunerativo = BigDecimal.ZERO;
	private BigDecimal totalDeduccion = BigDecimal.ZERO;
	private BigDecimal totalNeto = BigDecimal.ZERO;
	private Byte bloqueado = 0;
	private Long periodo;

}
