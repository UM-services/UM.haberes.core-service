package um.haberes.core.hexagonal.etec.arraigo_escala.application.usecases;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.arraigo_escala.application.exception.EscalaArraigoEtecException;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.model.EscalaArraigoEtec;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.ports.in.ReplaceEscalaArraigoEtecUseCase;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.ports.out.EscalaArraigoEtecRepository;

/**
 * Reemplaza la escala completa. Valida los tramos antes de borrar para no dejar la tabla vacía si
 * el payload es inválido; el borrado y la inserción van en la misma transacción.
 */
@Component
@RequiredArgsConstructor
public class ReplaceEscalaArraigoEtecUseCaseImpl implements ReplaceEscalaArraigoEtecUseCase {

    private static final BigDecimal CIEN = new BigDecimal("100.00");

    private final EscalaArraigoEtecRepository repository;

    @Transactional
    @Override
    public List<EscalaArraigoEtec> reemplazar(List<EscalaArraigoEtec> tramos) {
        List<EscalaArraigoEtec> normalizados = validar(tramos);
        repository.deleteAll();
        return repository.saveAll(normalizados);
    }

    private List<EscalaArraigoEtec> validar(List<EscalaArraigoEtec> tramos) {
        if (tramos == null || tramos.isEmpty()) {
            throw new EscalaArraigoEtecException("La escala de arraigo debe tener al menos un tramo");
        }
        Set<Integer> anios = new HashSet<>();
        for (EscalaArraigoEtec tramo : tramos) {
            if (tramo == null || tramo.getAniosMinimos() == null || tramo.getAniosMinimos() < 1) {
                throw new EscalaArraigoEtecException(
                        "Cada tramo debe indicar una antigüedad mínima de al menos 1 año");
            }
            if (tramo.getPorcentaje() == null) {
                throw new EscalaArraigoEtecException(
                        "El tramo de " + tramo.getAniosMinimos() + " año(s) no tiene porcentaje");
            }
            if (tramo.getPorcentaje().compareTo(BigDecimal.ZERO) < 0
                    || tramo.getPorcentaje().compareTo(CIEN) > 0) {
                throw new EscalaArraigoEtecException(
                        "El porcentaje del tramo de " + tramo.getAniosMinimos()
                                + " año(s) debe estar entre 0 y 100");
            }
            if (!anios.add(tramo.getAniosMinimos())) {
                throw new EscalaArraigoEtecException(
                        "La antigüedad mínima " + tramo.getAniosMinimos() + " está repetida");
            }
        }
        return tramos.stream()
                .map(tramo -> new EscalaArraigoEtec(null, tramo.getAniosMinimos(),
                        tramo.getPorcentaje().setScale(2, java.math.RoundingMode.HALF_UP)))
                .sorted(Comparator.comparing(EscalaArraigoEtec::getAniosMinimos))
                .toList();
    }
}
