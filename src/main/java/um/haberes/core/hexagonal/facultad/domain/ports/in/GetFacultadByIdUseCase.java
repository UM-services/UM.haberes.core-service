package um.haberes.core.hexagonal.facultad.domain.ports.in;
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
public interface GetFacultadByIdUseCase { Facultad getById(Integer id); }
