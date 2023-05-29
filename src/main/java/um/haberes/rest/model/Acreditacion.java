/**
 * 
 */
package um.haberes.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "anho", "mes" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Acreditacion extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5102428721384561238L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long acreditacionId;
	
	private Integer anho = 0;
	private Integer mes = 0;
	private Byte acreditado = 0;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime limiteNovedades;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaContable;
	
	private Integer ordenContable;
	private BigDecimal sueldosOriginal = BigDecimal.ZERO;
	private BigDecimal sueldosAjustados = BigDecimal.ZERO;
	private BigDecimal contribucionesPatronales = BigDecimal.ZERO;

}
