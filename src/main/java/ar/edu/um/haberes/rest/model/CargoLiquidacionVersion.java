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
@EqualsAndHashCode(callSuper=false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CargoLiquidacionVersion extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5351172599624659728L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long cargoLiquidacionVersionId;
	private Long legajoId;
	private Integer anho;
	private Integer mes;
	private Integer version = 0;
	private Integer dependenciaId;
	private Integer categoriaId;
	private BigDecimal basico = BigDecimal.ZERO;
	private BigDecimal horasJornada = BigDecimal.ZERO;
	private Integer jornada = 0;
	private Integer presentismo = 0;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaDesde;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime fechaHasta;
	
	private String situacion;

}
