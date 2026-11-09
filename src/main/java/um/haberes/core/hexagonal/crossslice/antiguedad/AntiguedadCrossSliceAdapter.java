package um.haberes.core.hexagonal.crossslice.antiguedad;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.exception.AntiguedadException;
import um.haberes.core.hexagonal.crossslice.antiguedad.model.AntiguedadView;
import um.haberes.core.model.AntiguedadEntity;
import um.haberes.core.service.AntiguedadService;
import um.haberes.core.service.facade.DesignacionToolService;

/**
 * Adaptador cross-slice de antigüedad: meses del registro y los dos índices del período.
 *
 * <p>Es una lectura: si no hay registro del período devuelve la vista vacía en lugar de disparar
 * el cálculo (el legacy calculaba al abrir el formulario, un efecto de escritura en una lectura
 * que no se replica).
 */
@Component
@RequiredArgsConstructor
public class AntiguedadCrossSliceAdapter implements AntiguedadCrossSlice {

    private final AntiguedadService antiguedadService;

    private final DesignacionToolService designacionToolService;

    @Override
    public AntiguedadView porLegajo(Long legajoId, Integer anho, Integer mes) {
        AntiguedadEntity antiguedad;
        try {
            antiguedad = antiguedadService.findByUnique(legajoId, anho, mes);
        } catch (AntiguedadException e) {
            return new AntiguedadView(null, null, null, null);
        }
        BigDecimal indiceDocente = null;
        BigDecimal indiceAdministrativo = null;
        List<BigDecimal> indices = designacionToolService.indiceAntiguedad(legajoId, anho, mes);
        if (indices != null && indices.size() > 0) {
            indiceDocente = indices.get(0);
        }
        if (indices != null && indices.size() > 1) {
            indiceAdministrativo = indices.get(1);
        }
        return new AntiguedadView(antiguedad.getMesesDocentes(), antiguedad.getMesesAdministrativos(),
                indiceDocente, indiceAdministrativo);
    }
}
