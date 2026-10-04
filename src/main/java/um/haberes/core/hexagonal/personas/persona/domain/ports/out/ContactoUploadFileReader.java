package um.haberes.core.hexagonal.personas.persona.domain.ports.out;

import java.util.List;

import um.haberes.core.hexagonal.personas.persona.domain.model.ContactoMail;
import um.haberes.core.hexagonal.personas.persona.domain.model.UploadedFile;

public interface ContactoUploadFileReader {

    List<ContactoMail> readContactos(UploadedFile file);
}
