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

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import um.haberes.rest.kotlin.model.Codigo;
import um.haberes.rest.kotlin.model.Dependencia;
import um.haberes.rest.kotlin.model.Persona;

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
public class NovedadUpload extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 4547887779697673841L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long novedadUploadId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Integer codigoId;
	private Integer dependenciaId;
	private BigDecimal importe = BigDecimal.ZERO;
	private String value = "";
	private Byte pendiente = 0;

	@OneToOne(optional = true)
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

	@OneToOne(optional = true)
	@JoinColumn(name = "codigoId", insertable = false, updatable = false)
	private Codigo codigo;

	@OneToOne(optional = true)
	@JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
	private Dependencia dependencia;

}
