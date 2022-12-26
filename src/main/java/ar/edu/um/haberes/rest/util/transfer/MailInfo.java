/**
 * 
 */
package ar.edu.um.haberes.rest.util.transfer;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author daniel
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -954221711698995796L;

	private Long legajoId;
	private String mailto;
	private String mailcc;
	private String replyto;
	private String subject;
	private String message;
}
