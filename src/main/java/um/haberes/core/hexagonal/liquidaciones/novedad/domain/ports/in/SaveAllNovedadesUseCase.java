package um.haberes.core.hexagonal.liquidaciones.novedad.domain.ports.in;

import java.util.List;

import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;

public interface SaveAllNovedadesUseCase {

    List<Novedad> saveAllNovedades(List<Novedad> novedades);
}
