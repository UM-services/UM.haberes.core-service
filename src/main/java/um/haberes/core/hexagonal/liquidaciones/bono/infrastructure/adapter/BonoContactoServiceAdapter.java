package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.adapter;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.exception.ContactoException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.ContactoRepository;
import um.haberes.core.model.ContactoEntity;
import um.haberes.core.service.ContactoService;

@Component
@RequiredArgsConstructor
public class BonoContactoServiceAdapter implements ContactoRepository {

    private final ContactoService contactoService;

    @Override
    public void upsertMailInstitucional(Long legajoId, String mailInstitucional) {
        ContactoEntity contacto;
        try {
            contacto = contactoService.findByLegajoId(legajoId);
        } catch (ContactoException e) {
            contacto = new ContactoEntity();
        }
        contacto.setLegajoId(legajoId);
        contacto.setMailInstitucional(mailInstitucional);
        contactoService.add(contacto);
    }
}
