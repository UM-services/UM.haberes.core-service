package um.haberes.core.hexagonal.geografica.domain.ports.in;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import java.util.List;
public interface GetAllGeograficasUseCase { List<Geografica> getAll(); }
