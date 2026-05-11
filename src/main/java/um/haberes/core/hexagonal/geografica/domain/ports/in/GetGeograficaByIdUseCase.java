package um.haberes.core.hexagonal.geografica.domain.ports.in;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import java.util.Optional;
public interface GetGeograficaByIdUseCase { Geografica getById(Integer id); }
