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
import lombok.ToString;
import um.haberes.rest.kotlin.model.Codigo;

/**
 * @author daniel
 *
 */
@Entity
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = { "legajoId", "anho", "mes", "codigoId" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Item extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8758620272361908858L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Integer codigoId;
	private String codigoNombre = "";
	private BigDecimal importe = BigDecimal.ZERO;

	@OneToOne
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

	@OneToOne
	@JoinColumn(name = "codigoId", insertable = false, updatable = false)
	private Codigo codigo;

	public String legajoKey() {
		return this.legajoId + "." + this.anho + "." + this.mes + "." + this.codigoId;
	}

}
