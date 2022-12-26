/**
 * 
 */
package ar.edu.um.haberes.rest.model.view.pk;

import java.io.Serializable;

import lombok.Data;

/**
 * @author daniel
 *
 */
@Data
public class AsignadoClasePk implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1430353018014970916L;

	private Long legajoId;
	private Integer dependenciaId;
	private Long cargoClaseId;

}
