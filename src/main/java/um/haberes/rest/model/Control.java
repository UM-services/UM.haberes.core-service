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
@EqualsAndHashCode(callSuper=false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Control extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6450459356294324489L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long controlId;

	private Integer anho;
	private Integer mes;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaDesde;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaHasta;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaPago;
	
	private String aporteJubilatorio;
	private String depositoBanco;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaDeposito;
	
	private BigDecimal doctorado = BigDecimal.ZERO;
	private BigDecimal maestria = BigDecimal.ZERO;
	private BigDecimal especializacion = BigDecimal.ZERO;
	private BigDecimal familiaNumerosa = BigDecimal.ZERO;
	private BigDecimal escuelaPrimaria = BigDecimal.ZERO;
	private BigDecimal escuelaSecundaria = BigDecimal.ZERO;
	private BigDecimal escuelaPrimariaNumerosa = BigDecimal.ZERO;
	private BigDecimal escuelaSecundariaNumerosa = BigDecimal.ZERO;
	private BigDecimal prenatal = BigDecimal.ZERO;
	private BigDecimal libre = BigDecimal.ZERO;
	private BigDecimal ayudaEscolar = BigDecimal.ZERO;
	private BigDecimal matrimonio = BigDecimal.ZERO;
	private BigDecimal nacimiento = BigDecimal.ZERO;
	private BigDecimal funcionDireccion = BigDecimal.ZERO;
	private BigDecimal mayorResponsabilidadPatrimonial = BigDecimal.ZERO;
	private BigDecimal polimedb = BigDecimal.ZERO;
	private BigDecimal polimedo = BigDecimal.ZERO;
	private BigDecimal montoeci = BigDecimal.ZERO;
	private BigDecimal valampo = BigDecimal.ZERO;
	private BigDecimal jubilaem = BigDecimal.ZERO;
	private BigDecimal inssjpem = BigDecimal.ZERO;
	private BigDecimal osociaem = BigDecimal.ZERO;
	private BigDecimal jubilpat = BigDecimal.ZERO;
	private BigDecimal inssjpat = BigDecimal.ZERO;
	private BigDecimal osocipat = BigDecimal.ZERO;
	private BigDecimal ansalpat = BigDecimal.ZERO;
	private BigDecimal salfapat = BigDecimal.ZERO;
	private BigDecimal minimoAporte = BigDecimal.ZERO;
	private BigDecimal maximoAporte = BigDecimal.ZERO;
	private BigDecimal mincontr = BigDecimal.ZERO;
	private BigDecimal maximo1sijp = BigDecimal.ZERO;
	private BigDecimal maximo2sijp = BigDecimal.ZERO;
	private BigDecimal maximo3sijp = BigDecimal.ZERO;
	private BigDecimal maximo4sijp = BigDecimal.ZERO;
	private BigDecimal maximo5sijp = BigDecimal.ZERO;
	private BigDecimal estadoDocenteTitular = BigDecimal.ZERO;
	private BigDecimal estadoDocenteAdjunto = BigDecimal.ZERO;
	private BigDecimal estadoDocenteAuxiliar = BigDecimal.ZERO;

}
