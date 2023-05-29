/**
 * 
 */
package um.haberes.rest.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "legajoId", "anho", "mes" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Antiguedad extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2098367118532318829L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long antiguedadId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Integer mesesDocentes = 0;
	private Integer mesesAdministrativos = 0;
	
	@OneToOne
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

}
