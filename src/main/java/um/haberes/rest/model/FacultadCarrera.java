/**
 * 
 */
package um.haberes.rest.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author daniel
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "facultadId", "planId", "carreraId" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FacultadCarrera extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -7884961596947783417L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long facultadCarreraId;

	private Integer facultadId;
	private Integer planId;
	private Integer carreraId;
	private String nombre = "";

//	@OneToOne
//	@JoinColumns({
//			@JoinColumn(name = "facultadId", referencedColumnName = "facultadId", insertable = false, updatable = false),
//			@JoinColumn(name = "planId", referencedColumnName = "planId", insertable = false, updatable = false) })
//	private FacultadPlan facultadPlan;

}
