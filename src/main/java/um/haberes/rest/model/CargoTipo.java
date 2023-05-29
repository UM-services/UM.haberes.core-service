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
public class CargoTipo extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3305190933765537796L;

	@Id
	private Integer cargoTipoId;

	private Byte aCargo = 0;
	private String nombre = "";
	private Integer precedencia = 0;

}
