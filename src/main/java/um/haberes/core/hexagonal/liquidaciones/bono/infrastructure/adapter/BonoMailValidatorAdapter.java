package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.adapter;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.MailValidator;
import um.haberes.core.service.facade.ToolService;

@Component
public class BonoMailValidatorAdapter implements MailValidator {

    @Override
    public boolean isValid(String mail) {
        return Boolean.TRUE.equals(ToolService.mailvalidate(mail));
    }
}
