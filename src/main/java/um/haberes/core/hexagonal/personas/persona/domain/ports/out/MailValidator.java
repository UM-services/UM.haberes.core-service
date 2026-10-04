package um.haberes.core.hexagonal.personas.persona.domain.ports.out;

public interface MailValidator {

    boolean isValid(String mail);
}
