/**
 * 
 */
package um.haberes.rest.model;

import java.io.Serializable;
import java.math.BigDecimal;

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
import lombok.ToString;

/**
 * @author daniel
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "legajoId", "anho", "mes" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Letra extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 2932628721169828332L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long letraId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private BigDecimal neto = BigDecimal.ZERO;
	private String cadena = "";

}
