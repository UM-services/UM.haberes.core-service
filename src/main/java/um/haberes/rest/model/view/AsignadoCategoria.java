/**
 * 
 */
package um.haberes.rest.model.view;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import um.haberes.rest.model.view.pk.AsignadoCategoriaPk;
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
@Table(name = "vw_asignado_categoria")
@IdClass(AsignadoCategoriaPk.class)
@NoArgsConstructor
@AllArgsConstructor
public class AsignadoCategoria implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3012590517683461417L;

	@Id
	private Long legajoId;

	@Id
	private Integer dependenciaId;

	@Id
	private Integer categoriaId;

	private Long periodoDesde;
	private Long periodoHasta;
	private BigDecimal basicoDesde = BigDecimal.ZERO;
	private BigDecimal basicoHasta = BigDecimal.ZERO;

}
