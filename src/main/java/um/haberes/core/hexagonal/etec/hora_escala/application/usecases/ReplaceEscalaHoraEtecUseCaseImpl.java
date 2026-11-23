package um.haberes.core.hexagonal.etec.hora_escala.application.usecases;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.out.ControlEtecRepository;
import um.haberes.core.hexagonal.etec.hora_escala.application.exception.EscalaHoraEtecException;
import um.haberes.core.hexagonal.etec.hora_escala.application.exception.EscalaHoraEtecPeriodoException;
import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;
import um.haberes.core.hexagonal.etec.hora_escala.domain.ports.in.ReplaceEscalaHoraEtecUseCase;
import um.haberes.core.hexagonal.etec.hora_escala.domain.ports.out.EscalaHoraEtecRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

/**
 * Reemplaza la escala de horas del período. Valida todos los tramos antes de borrar para no
 * dejar la escala vacía si el payload es inválido; el borrado y la inserción comparten
 * transacción.
 *
 * <p>Una lista vacía es válida: significa que el período no liquida horas de clase ETEC.</p>
 */
@Component
@RequiredArgsConstructor
public class ReplaceEscalaHoraEtecUseCaseImpl implements ReplaceEscalaHoraEtecUseCase {

    private final ControlEtecRepository controlEtecRepository;

    private final EscalaHoraEtecRepository repository;

    private final CategoriaRepository categoriaRepository;

    @Transactional
    @Override
    public List<EscalaHoraEtec> reemplazar(Integer anho, Integer mes, List<EscalaHoraEtec> tramos) {
        Long controlEtecId = controlEtecRepository.findByPeriodo(anho, mes)
                .map(control -> control.getControlEtecId())
                .orElseThrow(() -> new EscalaHoraEtecPeriodoException(anho, mes));

        List<EscalaHoraEtec> normalizados = validar(controlEtecId, tramos);
        repository.deleteAllByControlEtecId(controlEtecId);
        if (normalizados.isEmpty()) {
            return List.of();
        }
        return repository.saveAll(normalizados);
    }

    private List<EscalaHoraEtec> validar(Long controlEtecId, List<EscalaHoraEtec> tramos) {
        if (tramos == null || tramos.isEmpty()) {
            return List.of();
        }
        String error = validarTramos(tramos);
        if (error != null) {
            throw new EscalaHoraEtecException(error);
        }
        return tramos.stream()
                .map(tramo -> EscalaHoraEtec.builder()
                        .escalaHoraEtecId(null)
                        .controlEtecId(controlEtecId)
                        .categoriaId(tramo.getCategoriaId())
                        .horasDesde(tramo.getHorasDesde().setScale(2, RoundingMode.HALF_UP))
                        .horasHasta(tramo.getHorasHasta().setScale(2, RoundingMode.HALF_UP))
                        .montoFijo(importe(tramo.getMontoFijo()))
                        .valorHora(importe(tramo.getValorHora()))
                        .build())
                .sorted(Comparator.comparing(EscalaHoraEtec::getHorasDesde))
                .toList();
    }

    private String validarTramos(List<EscalaHoraEtec> tramos) {
        Map<Integer, Categoria> categorias = new HashMap<>();
        for (EscalaHoraEtec tramo : tramos) {
            if (tramo == null || tramo.getHorasDesde() == null || tramo.getHorasHasta() == null) {
                return "Cada tramo debe indicar las horas desde y hasta";
            }
            if (tramo.getCategoriaId() == null) {
                return "Cada tramo debe indicar la categoría que liquida sus horas";
            }
            Categoria categoria = categorias.computeIfAbsent(tramo.getCategoriaId(),
                    categoriaId -> categoriaRepository.findByCategoriaId(categoriaId).orElse(null));
            if (categoria == null) {
                return "La categoría " + tramo.getCategoriaId() + " no existe";
            }
            if (!Byte.valueOf((byte) 1).equals(categoria.getEtecHoras())) {
                return "La categoría " + categoria.getCategoriaId() + " (" + categoria.getNombre()
                        + ") no está marcada como horas ETEC";
            }
            if (tramo.getHorasDesde().compareTo(BigDecimal.ZERO) < 0
                    || tramo.getHorasHasta().compareTo(BigDecimal.ZERO) < 0) {
                return "Las horas de un tramo no pueden ser negativas";
            }
            if (tramo.getHorasHasta().compareTo(tramo.getHorasDesde()) < 0) {
                return "El tramo que empieza en " + tramo.getHorasDesde().stripTrailingZeros().toPlainString()
                        + " hora(s) termina antes de empezar";
            }
            if (negativo(tramo.getMontoFijo())) {
                return "El monto fijo del tramo que empieza en "
                        + tramo.getHorasDesde().stripTrailingZeros().toPlainString()
                        + " hora(s) no puede ser negativo";
            }
            if (negativo(tramo.getValorHora())) {
                return "El valor por hora del tramo que empieza en "
                        + tramo.getHorasDesde().stripTrailingZeros().toPlainString()
                        + " hora(s) no puede ser negativo";
            }
        }

        List<EscalaHoraEtec> ordenados = tramos.stream()
                .sorted(Comparator.comparing(EscalaHoraEtec::getHorasDesde))
                .toList();
        for (int i = 1; i < ordenados.size(); i++) {
            EscalaHoraEtec anterior = ordenados.get(i - 1);
            EscalaHoraEtec actual = ordenados.get(i);
            if (actual.getHorasDesde().compareTo(anterior.getHorasHasta()) <= 0) {
                return "Los tramos se superponen entre "
                        + anterior.getHorasDesde().stripTrailingZeros().toPlainString() + " y "
                        + anterior.getHorasHasta().stripTrailingZeros().toPlainString()
                        + " hora(s)";
            }
        }
        return null;
    }

    private boolean negativo(BigDecimal valor) {
        return valor != null && valor.compareTo(BigDecimal.ZERO) < 0;
    }

    private BigDecimal importe(BigDecimal valor) {
        return valor == null ? new BigDecimal("0.00") : valor.setScale(2, RoundingMode.HALF_UP);
    }
}
