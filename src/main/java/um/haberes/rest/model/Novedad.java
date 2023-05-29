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
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = { "legajoId", "anho", "mes", "codigoId", "dependenciaId" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Novedad extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 6760576346714718901L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long novedadId;
	
	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Integer codigoId;
	private Integer dependenciaId;
	private BigDecimal importe = BigDecimal.ZERO;
	private String value = "";
	private String observaciones;
	private Byte importado = 0;
	private Long novedadUploadId;

	@OneToOne
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

	@OneToOne
	@JoinColumn(name = "codigoId", insertable = false, updatable = false)
	private Codigo codigo;

	@OneToOne
	@JoinColumn(name = "dependenciaId", insertable = false, updatable = false)
	private Dependencia dependencia;

}
