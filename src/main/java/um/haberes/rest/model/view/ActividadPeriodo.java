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
@Table(name = "vw_actividad_periodo")
@Immutable
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class ActividadPeriodo extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 6377395067331172571L;

	@Id
	private Long actividadId;

	private Long legajoId;
	private Integer anho;
	private Integer mes;
	private Byte docente;
	private Byte otras;
	private Byte clases;
	private Integer dependenciaId;
	private Long periodo;
	
}
