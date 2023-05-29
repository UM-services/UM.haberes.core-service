/**
 * 
 */
package um.haberes.rest.model.view;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import um.haberes.rest.model.Auditable;
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
@Table(name = "vw_categoria_search")
@EqualsAndHashCode(callSuper = false)
@Immutable
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaSearch extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9112856243004702540L;

	@Id
	private Integer categoriaId;

	private String nombre;
	private BigDecimal basico;
	private String search;

}
