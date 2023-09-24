/**
 *
 */
package um.haberes.rest.controller.facade;

import java.util.List;

import jakarta.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import um.haberes.rest.service.facade.MailingService;
import um.haberes.rest.util.transfer.MailInfo;

/**
 * @author daniel
 *
 */
@RestController
@RequestMapping("/mailing")
public class MailingController {

    private final MailingService service;

    @Autowired
    public MailingController(MailingService service) {
        this.service = service;
    }

    @PostMapping("/")
    public ResponseEntity<Void> mailing(@RequestBody List<MailInfo> mails) throws MessagingException {
        service.mailing(mails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
