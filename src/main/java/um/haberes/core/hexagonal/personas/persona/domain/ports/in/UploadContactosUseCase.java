package um.haberes.core.hexagonal.personas.persona.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.domain.model.UploadedFile;

public interface UploadContactosUseCase {

    List<Persona> uploadContactos(UploadedFile file);
}
