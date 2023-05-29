/**
 * 
 */
package um.haberes.rest.model.view;

import java.io.Serializable;

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
@Table(name = "vw_antiguedad_periodo")
@Immutable
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class AntiguedadPeriodo extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7431994123162904423L;

	@Id
	private Long antiguedadId;

	private Long legajoId;
	private Integer anho;
	private Integer mes;
	private Integer mesesDocentes;
	private Integer mesesAdministrativos;
	private Long periodo;
	
}
