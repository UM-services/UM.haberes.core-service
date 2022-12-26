/**
 * 
 */
package ar.edu.um.haberes.rest.model.view;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import ar.edu.um.haberes.rest.model.view.pk.CategoriaByPeriodoPK;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@Entity
@Immutable
@Table(name = "vw_categoria_by_periodo")
@IdClass(CategoriaByPeriodoPK.class)
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaByPeriodo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8368046732862078891L;

	@Id
	private Integer anho;

	@Id
	private Integer mes;
	
	@Id
	private Integer categoriaId;
	
}
