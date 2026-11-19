package um.haberes.core.hexagonal.liquidaciones.sijp.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.ArchivoSijp;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.VarianteSijp;

public interface GenerarArchivoSijpUseCase {

    ArchivoSijp generar(Integer anho, Integer mes, VarianteSijp variante);

}
