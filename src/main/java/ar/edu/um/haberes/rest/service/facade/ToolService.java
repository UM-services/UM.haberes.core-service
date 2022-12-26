/**
 * 
 */
package ar.edu.um.haberes.rest.service.facade;

import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;

import org.springframework.stereotype.Service;

/**
 * @author daniel
 *
 */
@Service
public class ToolService {

	public static Boolean mailvalidate(String mail) {
		try {
			InternetAddress address = new InternetAddress(mail);
			address.validate();
		} catch (AddressException e) {
			return false;
		}
		return true;
	}

}
