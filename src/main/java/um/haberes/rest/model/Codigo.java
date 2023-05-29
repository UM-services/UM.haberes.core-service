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
import lombok.ToString;

/**
 * @author daniel
 *
 */
@Entity
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Codigo extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 2630433768400508483L;

	@Id
	private Integer codigoId;

	private String nombre = "";
	private Byte docente = 0;
	private Byte noDocente = 0;
	private Byte transferible = 0;

}
