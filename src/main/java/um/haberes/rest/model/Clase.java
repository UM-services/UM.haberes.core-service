/**
 * 
 */
package um.haberes.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
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
@EqualsAndHashCode(callSuper=false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Clase extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3367207143385561110L;

	@Id
	private Integer claseId;

	private String nombre = "";
	private BigDecimal valorHora = BigDecimal.ZERO;

}
