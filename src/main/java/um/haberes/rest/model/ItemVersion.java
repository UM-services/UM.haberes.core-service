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
public class ItemVersion extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 8898066902942251703L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long itemVersionId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Integer codigoId;
	private String codigoNombre = "";
	private BigDecimal importe = BigDecimal.ZERO;

}
