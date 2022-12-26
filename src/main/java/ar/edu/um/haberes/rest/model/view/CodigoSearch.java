/**
 * 
 */
package ar.edu.um.haberes.rest.model.view;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import ar.edu.um.haberes.rest.model.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Table(name = "vw_codigo_search")
@Immutable
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class CodigoSearch extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -7676533796537189483L;

	@Id
	private Integer codigoId;

	private String nombre;
	private Byte docente;
	private Byte noDocente;
	private String search;

}
