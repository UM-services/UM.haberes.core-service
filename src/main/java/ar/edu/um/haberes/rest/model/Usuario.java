/**
 * 
 */
package ar.edu.um.haberes.rest.model;

import java.io.Serializable;
import java.time.OffsetDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import com.fasterxml.jackson.annotation.JsonFormat;

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
public class Usuario extends Auditable implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1942286773061290379L;

	@Id
	private Long legajoId;

	private String password = "";

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ", timezone = "UTC")
	private OffsetDateTime lastLog;

	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usuarioId;

}
