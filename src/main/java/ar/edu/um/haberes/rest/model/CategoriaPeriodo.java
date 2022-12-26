/**
 * 
 */
package ar.edu.um.haberes.rest.model;

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
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "categoriaId", "anho", "mes" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaPeriodo extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9159061658289824758L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoriaPeriodoId;

	private Integer categoriaId;
	private Integer anho = 0;
	private Integer mes = 0;
	private String nombre = "";
	private BigDecimal basico = BigDecimal.ZERO;
	private Byte docente = 0;
	private Byte noDocente = 0;
	private Byte liquidaPorHora = 0;

}
