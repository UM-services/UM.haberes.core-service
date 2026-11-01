package um.haberes.core.hexagonal.personas.persona.application.usecases;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.personas.persona.domain.model.ContactoMail;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.model.UploadedFile;
import um.haberes.core.hexagonal.personas.persona.domain.ports.in.UploadContactosUseCase;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.ContactoRepository;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.ContactoUploadFileReader;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.MailValidator;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.PersonaRepository;

@Component
@RequiredArgsConstructor
public class UploadContactosUseCaseImpl implements UploadContactosUseCase {

    private final PersonaRepository personaRepository;

    private final ContactoRepository contactoRepository;

    private final ContactoUploadFileReader contactoUploadFileReader;

    private final MailValidator mailValidator;

    @Override
    public List<Persona> uploadContactos(UploadedFile file) {
        List<ContactoMail> filas = contactoUploadFileReader.readContactos(file);
        List<ContactoMail> contactos = new ArrayList<>();
        List<Long> legajos = new ArrayList<>();
        for (ContactoMail fila : filas) {
            if (fila.getLegajoId() == null || fila.getLegajoId() <= 0) {
                continue;
            }
            ContactoMail contacto = contactoRepository.findByLegajoId(fila.getLegajoId())
                    .orElseGet(() -> ContactoMail.builder().legajoId(fila.getLegajoId()).build());
            boolean actualizado = false;
            if (fila.getMailPersonal() != null && mailValidator.isValid(fila.getMailPersonal())) {
                contacto.setMailPersonal(fila.getMailPersonal());
                actualizado = true;
            }
            if (fila.getMailInstitucional() != null && mailValidator.isValid(fila.getMailInstitucional())) {
                contacto.setMailInstitucional(fila.getMailInstitucional());
                actualizado = true;
            }
            if (actualizado) {
                contactos.add(contacto);
                legajos.add(fila.getLegajoId());
            }
        }
        contactoRepository.saveAll(contactos);
        return personaRepository.findAllByLegajoIds(legajos);
    }
}
