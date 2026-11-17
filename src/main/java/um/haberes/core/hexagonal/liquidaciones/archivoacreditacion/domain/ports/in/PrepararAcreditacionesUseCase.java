package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.PreparacionAcreditacion;

public interface PrepararAcreditacionesUseCase {

    PreparacionAcreditacion prepararPeriodo(Integer anho, Integer mes);

    PreparacionAcreditacion prepararPorCodigo(Integer anho, Integer mes, Integer codigoId);

    PreparacionAcreditacion prepararLegajo(Long legajoId, Integer anho, Integer mes);
}
