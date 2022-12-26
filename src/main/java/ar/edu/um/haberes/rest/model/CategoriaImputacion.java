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
@Table(uniqueConstraints = {
		@UniqueConstraint(columnNames = { "dependenciaId", "facultadId", "geograficaId", "categoriaId" }) })
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaImputacion extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 8731089494132357177L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoriaImputacionId;

	private Integer dependenciaId;
	private Integer facultadId;
	private Integer geograficaId;
	private Integer categoriaId;
	private BigDecimal cuentaSueldos;
	private BigDecimal cuentaAportes;

	public String key() {
		return dependenciaId + "." + facultadId + "." + geograficaId + "." + categoriaId;
	}

}
