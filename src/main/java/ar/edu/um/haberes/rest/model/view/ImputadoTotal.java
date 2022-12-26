/**
 * 
 */
package ar.edu.um.haberes.rest.model.view;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

import org.hibernate.annotations.Immutable;

import ar.edu.um.haberes.rest.model.view.pk.ImputadoTotalPk;
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
@Table(name = "vw_imputado_total")
@IdClass(ImputadoTotalPk.class)
@NoArgsConstructor
@AllArgsConstructor
public class ImputadoTotal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4342591837697086222L;
	
	@Id
	private Integer anho;
	
	@Id
	private Integer mes;
	
	private BigDecimal total;

}
