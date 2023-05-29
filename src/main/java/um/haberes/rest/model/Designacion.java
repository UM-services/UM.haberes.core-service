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
public class Designacion extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -2356325992639978570L;

	@Id
	private Integer categoriaId;

	private Integer designacionTipoId;
	private Integer cargoTipoId;
	private Byte anual = 0;
	private Byte semestral = 0;
	private Byte aCargo = 0;

}
