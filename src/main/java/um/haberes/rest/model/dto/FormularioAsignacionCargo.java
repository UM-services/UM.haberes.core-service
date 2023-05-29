/**
 * 
 */
package um.haberes.rest.model.dto;

import java.io.Serializable;
import java.util.List;

import um.haberes.rest.model.Categoria;
import um.haberes.rest.model.Dependencia;
import um.haberes.rest.model.Facultad;
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
public class FormularioAsignacionCargo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1349554321974212244L;

	private List<Categoria> categorias;
	private List<Categoria> categoriasAsignables;
	private List<Dependencia> dependencias;
	private List<Facultad> facultades;

}
