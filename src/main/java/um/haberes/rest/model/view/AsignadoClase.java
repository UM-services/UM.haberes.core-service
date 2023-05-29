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

import um.haberes.rest.model.view.pk.AsignadoClasePk;
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
@Table(name = "vw_asignado_clase")
@IdClass(AsignadoClasePk.class)
@NoArgsConstructor
@AllArgsConstructor
public class AsignadoClase implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 9025541214131527308L;

	@Id
	private Long legajoId;
	
	@Id
	private Integer dependenciaId;

	@Id
	private Long cargoClaseId;

	private Long periodoDesde;
	private Long periodoHasta;
	private BigDecimal basicoDesde = BigDecimal.ZERO;
	private BigDecimal basicoHasta = BigDecimal.ZERO;

}
