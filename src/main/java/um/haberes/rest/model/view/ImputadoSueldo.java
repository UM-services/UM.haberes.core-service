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

import um.haberes.rest.model.view.pk.ImputadoSueldoPk;
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
@Table(name = "vw_imputado_sueldo")
@IdClass(ImputadoSueldoPk.class)
@NoArgsConstructor
@AllArgsConstructor
public class ImputadoSueldo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5028212004561357534L;
	
	@Id
	private Integer anho;
	
	@Id
	private Integer mes;
	
	@Id
	private BigDecimal cuentaSueldos;
	
	private BigDecimal totalImputado;

}
