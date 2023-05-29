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
public class Situacion extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2895551417029745822L;

	@Id
	private Integer situacionId;

	private String nombre = "";
	private Byte interino = 0;
	private Byte ordinario = 0;
	private Byte planta = 0;
	private Byte contratado = 0;
	private Byte secundario = 0;
	
}
