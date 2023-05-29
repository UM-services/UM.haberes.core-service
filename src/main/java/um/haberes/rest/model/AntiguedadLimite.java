/**
 * 
 */
package um.haberes.rest.model;

import java.io.Serializable;

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
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AntiguedadLimite extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -5174803627592479795L;

	@Id
	private Integer desde = 0;

	private Integer hasta = 0;
	private Integer porcentaje = 0;

}
