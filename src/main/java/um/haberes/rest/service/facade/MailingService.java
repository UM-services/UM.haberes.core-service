/**
 * 
 */
package um.haberes.rest.service.facade;

import java.util.ArrayList;
import java.util.List;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import um.haberes.rest.util.transfer.MailInfo;
import lombok.extern.slf4j.Slf4j;

/**
 * @author daniel
 *
 */
@Service
@Slf4j
public class MailingService {
	@Autowired
	private JavaMailSender sender;

	public void mailing(List<MailInfo> mails) throws MessagingException {
		for (MailInfo mailinfo : mails) {
			log.debug("MailInfo -> " + mailinfo);
			// Envia correo
			MimeMessage message = sender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true);
			List<String> addresses = new ArrayList<String>();
			if (!mailinfo.getMailto().trim().equals(""))
				addresses.add(mailinfo.getMailto().trim());
			if (!mailinfo.getMailcc().trim().equals(""))
				addresses.add(mailinfo.getMailcc().trim());
			try {
				helper.setTo(addresses.toArray(new String[addresses.size()]));
				helper.setText(mailinfo.getMessage());
				helper.setReplyTo(mailinfo.getReplyto());
				helper.setSubject(mailinfo.getSubject());
			} catch (MessagingException e) {
				log.debug(e.getMessage());
			}
			sender.send(message);
		}
	}

}
