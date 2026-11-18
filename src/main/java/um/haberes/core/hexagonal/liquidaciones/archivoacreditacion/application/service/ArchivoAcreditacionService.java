package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.application.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.ArchivoAcreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.ArchivoAcreditacionFiltro;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.PreparacionAcreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.ports.in.GenerarArchivoAcreditacionUseCase;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.ports.in.PrepararAcreditacionesUseCase;

@Service
@RequiredArgsConstructor
public class ArchivoAcreditacionService {

    private final GenerarArchivoAcreditacionUseCase generarArchivoAcreditacionUseCase;
    private final PrepararAcreditacionesUseCase prepararAcreditacionesUseCase;

    public ArchivoAcreditacion generarSantander(ArchivoAcreditacionFiltro filtro) {
        return generarArchivoAcreditacionUseCase.generarSantander(filtro);
    }

    public ArchivoAcreditacion generarOtrosBancos(ArchivoAcreditacionFiltro filtro) {
        return generarArchivoAcreditacionUseCase.generarOtrosBancos(filtro);
    }

    public PreparacionAcreditacion prepararPeriodo(Integer anho, Integer mes) {
        return prepararAcreditacionesUseCase.prepararPeriodo(anho, mes);
    }

    public PreparacionAcreditacion prepararPorCodigo(Integer anho, Integer mes, Integer codigoId) {
        return prepararAcreditacionesUseCase.prepararPorCodigo(anho, mes, codigoId);
    }

    public PreparacionAcreditacion prepararLegajo(Long legajoId, Integer anho, Integer mes) {
        return prepararAcreditacionesUseCase.prepararLegajo(legajoId, anho, mes);
    }
}
