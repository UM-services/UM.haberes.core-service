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

import ar.edu.um.haberes.rest.model.view.pk.ImputadoAportePk;
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
@Table(name = "vw_imputado_aporte")
@IdClass(ImputadoAportePk.class)
@NoArgsConstructor
@AllArgsConstructor
public class ImputadoAporte implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7308260193562172780L;
	
	@Id
	private Integer anho;
	
	@Id
	private Integer mes;
	
	@Id
	private BigDecimal cuentaAportes;
	
	private BigDecimal porcentajeImputado;
	
}
