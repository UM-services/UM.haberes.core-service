/**
 * 
 */
package ar.edu.um.haberes.rest.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

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
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Anotador extends Auditable implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = -1029471565435729110L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long anotadorId;

	private Long legajoId;
	private Integer anho = 0;
	private Integer mes = 0;
	private Integer facultadId;
	private String anotacion;
	private Byte visado = 0;
	private String ipVisado;
	private String user;
	private String respuesta;
	private Byte autorizado = 0;
	private Byte rechazado = 0;
	private Byte rectorado = 0;
	private Byte transferido = 0;

	@OneToOne
	@JoinColumn(name = "legajoId", insertable = false, updatable = false)
	private Persona persona;

	@OneToOne
	@JoinColumn(name = "facultadId", insertable = false, updatable = false)
	private Facultad facultad;

}
