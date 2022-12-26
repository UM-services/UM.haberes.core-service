/**
 * 
 */
package ar.edu.um.haberes.rest.model.dto;

import java.io.Serializable;
import java.util.List;

import ar.edu.um.haberes.rest.model.Contacto;
import ar.edu.um.haberes.rest.model.LegajoControl;
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
public class FormularioImprimir implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 7096435843257056301L;
	
	private List<LegajoControl> legajoControls;
	private List<Contacto> contactos;

}
