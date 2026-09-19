package um.haberes.core.hexagonal.personas.persona.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;

public interface GetNoDocentesUseCase {

    List<Persona> getNoDocentes(Integer anho, Integer mes);
}
