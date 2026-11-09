package um.haberes.core.hexagonal.crossslice.novedad;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.exception.NovedadException;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.service.NovedadService;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;

/** Adaptador cross-slice de novedades. */
@Component
@RequiredArgsConstructor
public class NovedadCrossSliceAdapter implements NovedadCrossSlice {

    private final NovedadService novedadService;

    @Override
    public List<NovedadView> porLegajo(Long legajoId, Integer anho, Integer mes) {
        return novedadService.findAllByLegajo(legajoId, anho, mes).stream()
                .map(this::toView)
                .toList();
    }

    @Override
    public Optional<NovedadView> porClaveSinDependencia(Long legajoId, Integer anho, Integer mes,
                                                        Integer codigoId) {
        try {
            return Optional.of(toView(novedadService.findByUniqueWithoutDependencia(legajoId, anho, mes, codigoId)));
        } catch (NovedadException e) {
            return Optional.empty();
        }
    }

    @Override
    public NovedadView crear(Long legajoId, Integer anho, Integer mes, Integer codigoId, BigDecimal importe,
                             String value, String observaciones) {
        Novedad novedad = Novedad.builder()
                .legajoId(legajoId)
                .anho(anho)
                .mes(mes)
                .codigoId(codigoId)
                .importe(importe)
                .value(value)
                .observaciones(observaciones)
                // La columna `importado` es NOT NULL en la base: el alta manual no viene de una
                // importación, así que se persiste como 0 (igual que el default de la entidad).
                .importado((byte) 0)
                .build();
        return toView(recargar(novedadService.add(novedad)));
    }

    @Override
    public NovedadView actualizar(Long novedadId, BigDecimal importe, String value, String observaciones) {
        Novedad existing = novedadService.findByNovedadId(novedadId);
        Novedad novedad = Novedad.builder()
                .novedadId(novedadId)
                .legajoId(existing.getLegajoId())
                .anho(existing.getAnho())
                .mes(existing.getMes())
                .codigoId(existing.getCodigoId())
                .dependenciaId(existing.getDependenciaId())
                .importe(importe)
                .value(value)
                .observaciones(observaciones)
                .importado(existing.getImportado())
                .novedadUploadId(existing.getNovedadUploadId())
                .build();
        return toView(recargar(novedadService.update(novedad, novedadId)));
    }

    @Override
    public void eliminar(Long novedadId) {
        novedadService.deleteByNovedadId(novedadId);
    }

    /**
     * El guardado devuelve el dominio armado desde la entidad insertada/actualizada, sin las
     * relaciones (`codigo`, `dependencia`). Se relee por id para devolver la vista completa.
     */
    private Novedad recargar(Novedad saved) {
        if (saved == null || saved.getNovedadId() == null) {
            return saved;
        }
        try {
            return novedadService.findByNovedadId(saved.getNovedadId());
        } catch (NovedadException e) {
            return saved;
        }
    }

    private NovedadView toView(Novedad novedad) {
        if (novedad == null) {
            return null;
        }
        String codigoNombre = novedad.getCodigo() == null ? null : novedad.getCodigo().getNombre();
        return new NovedadView(novedad.getNovedadId(), novedad.getCodigoId(), codigoNombre,
                novedad.getImporte(), novedad.getValue(), novedad.getObservaciones(),
                novedad.getDependenciaId());
    }
}
