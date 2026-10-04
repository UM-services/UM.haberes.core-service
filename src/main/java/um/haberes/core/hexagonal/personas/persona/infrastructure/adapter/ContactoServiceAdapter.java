package um.haberes.core.hexagonal.personas.persona.infrastructure.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.exception.ContactoException;
import um.haberes.core.hexagonal.personas.persona.domain.model.ContactoMail;
import um.haberes.core.hexagonal.personas.persona.domain.ports.out.ContactoRepository;
import um.haberes.core.model.ContactoEntity;
import um.haberes.core.service.ContactoService;

@Component
@RequiredArgsConstructor
public class ContactoServiceAdapter implements ContactoRepository {

    private final ContactoService contactoService;

    @Override
    public Optional<ContactoMail> findByLegajoId(Long legajoId) {
        ContactoEntity contacto;
        try {
            contacto = contactoService.findByLegajoId(legajoId);
        } catch (ContactoException e) {
            return Optional.empty();
        }
        return Optional.of(ContactoMail.builder()
                .legajoId(contacto.getLegajoId())
                .mailPersonal(contacto.getMailPersonal())
                .mailInstitucional(contacto.getMailInstitucional())
                .build());
    }

    @Override
    public void saveAll(List<ContactoMail> contactos) {
        List<ContactoEntity> entities = new ArrayList<>();
        for (ContactoMail contacto : contactos) {
            ContactoEntity entity;
            try {
                entity = contactoService.findByLegajoId(contacto.getLegajoId());
            } catch (ContactoException e) {
                entity = new ContactoEntity();
            }
            entity.setLegajoId(contacto.getLegajoId());
            if (contacto.getMailPersonal() != null) {
                entity.setMailPersonal(contacto.getMailPersonal());
            }
            if (contacto.getMailInstitucional() != null) {
                entity.setMailInstitucional(contacto.getMailInstitucional());
            }
            entities.add(entity);
        }
        contactoService.saveAll(entities);
    }
}
