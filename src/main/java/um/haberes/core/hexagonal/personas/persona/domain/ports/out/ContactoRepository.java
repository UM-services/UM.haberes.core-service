package um.haberes.core.hexagonal.personas.persona.domain.ports.out;

import java.util.List;
import java.util.Optional;

import um.haberes.core.hexagonal.personas.persona.domain.model.ContactoMail;

public interface ContactoRepository {

    Optional<ContactoMail> findByLegajoId(Long legajoId);

    void saveAll(List<ContactoMail> contactos);
}
