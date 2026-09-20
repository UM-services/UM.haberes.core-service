package um.haberes.core.hexagonal.personas.persona.infrastructure.adapter;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.personas.persona.domain.ports.out.MailValidator;
import um.haberes.core.service.facade.ToolService;

@Component
public class ToolServiceMailValidator implements MailValidator {

    @Override
    public boolean isValid(String mail) {
        return Boolean.TRUE.equals(ToolService.mailvalidate(mail));
    }
}
