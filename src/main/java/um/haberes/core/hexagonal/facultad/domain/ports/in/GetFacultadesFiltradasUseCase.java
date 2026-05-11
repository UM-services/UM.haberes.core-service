package um.haberes.core.hexagonal.facultad.domain.ports.in;
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import java.util.List;
public interface GetFacultadesFiltradasUseCase { List<Facultad> getFacultadesFiltradas(); }
