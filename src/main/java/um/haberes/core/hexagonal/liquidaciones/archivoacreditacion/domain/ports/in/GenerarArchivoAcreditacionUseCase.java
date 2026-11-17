package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.ArchivoAcreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.ArchivoAcreditacionFiltro;

public interface GenerarArchivoAcreditacionUseCase {

    ArchivoAcreditacion generarSantander(ArchivoAcreditacionFiltro filtro);

    ArchivoAcreditacion generarOtrosBancos(ArchivoAcreditacionFiltro filtro);
}
