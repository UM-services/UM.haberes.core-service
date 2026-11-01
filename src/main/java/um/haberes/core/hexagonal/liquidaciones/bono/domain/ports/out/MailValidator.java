package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out;

public interface MailValidator {

    boolean isValid(String mail);
}
