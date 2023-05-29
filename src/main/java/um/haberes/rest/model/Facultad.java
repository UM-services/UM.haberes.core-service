/**
 * 
 */
package um.haberes.rest.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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
@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Facultad extends Auditable implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4209410757170860237L;

	@Id
	private Integer facultadId;
	
	private String nombre = "";
	private String reducido = "";
	private String server = "";
	private String backendServer = "";
	private Integer backendPort = 0;
	private String dbName = "";
	private String dsn = "";
	
}
