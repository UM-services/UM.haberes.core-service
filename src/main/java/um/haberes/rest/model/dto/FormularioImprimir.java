/**
 * 
 */
package um.haberes.rest.model.dto;

import java.io.Serializable;
import java.util.List;

import um.haberes.rest.kotlin.model.Contacto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import um.haberes.rest.kotlin.model.LegajoControl;

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
