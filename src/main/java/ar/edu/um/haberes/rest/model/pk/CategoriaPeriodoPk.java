/**
 * 
 */
package ar.edu.um.haberes.rest.model.pk;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaPeriodoPk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3324874805009547255L;

	private Integer categoriaId;
	private Integer anho;
	private Integer mes;
}
