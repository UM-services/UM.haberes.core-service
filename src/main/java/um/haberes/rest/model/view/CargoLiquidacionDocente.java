/**
 * 
 */
package um.haberes.rest.model.view;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Table(name = "vw_cargo_liquidacion_docente")
@NoArgsConstructor
@AllArgsConstructor
public class CargoLiquidacionDocente implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 595869144924093720L;

	@Id
	private String uniqueId;

	private Long legajoId;
	private Integer anho;
	private Integer mes;

}
