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

/**
 * @author daniel
 *
 */
@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "anho", "mes" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeguridadSocial extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 651231624584908382L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long seguridadSocialId;

	private Integer anho = 0;
	private Integer mes = 0;
	private BigDecimal cc351 = BigDecimal.ZERO;
	private BigDecimal cc301 = BigDecimal.ZERO;
	private BigDecimal cc352 = BigDecimal.ZERO;
	private BigDecimal cc302 = BigDecimal.ZERO;
	private BigDecimal cc312 = BigDecimal.ZERO;
	private BigDecimal cc028 = BigDecimal.ZERO;

}
