package um.haberes.core.hexagonal.etec.arraigo_escala.application.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.model.EscalaArraigoEtec;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.ports.in.GetEscalaArraigoEtecUseCase;
import um.haberes.core.hexagonal.etec.arraigo_escala.domain.ports.in.ReplaceEscalaArraigoEtecUseCase;

/**
 * Escala global de arraigo ETEC. El motor la consulta con los años de antigüedad docente del
 * legajo: se usa el tramo de mayor antigüedad mínima que no supere ese valor y, si no hay ninguno,
 * el arraigo es cero.
 */
@Service
@RequiredArgsConstructor
public class EscalaArraigoEtecService {

    private final GetEscalaArraigoEtecUseCase getEscalaArraigoEtecUseCase;

    private final ReplaceEscalaArraigoEtecUseCase replaceEscalaArraigoEtecUseCase;

    public List<EscalaArraigoEtec> findAll() {
        return getEscalaArraigoEtecUseCase.findAll();
    }

    public List<EscalaArraigoEtec> reemplazar(List<EscalaArraigoEtec> tramos) {
        return replaceEscalaArraigoEtecUseCase.reemplazar(tramos);
    }

    /** Porcentaje de arraigo para una antigüedad docente en años; cero si no alcanza el primer tramo. */
    public BigDecimal porcentajeParaAnios(int anios) {
        return getEscalaArraigoEtecUseCase.findAll().stream()
                .filter(tramo -> tramo.getAniosMinimos() != null && tramo.getAniosMinimos() <= anios)
                .max(Comparator.comparing(EscalaArraigoEtec::getAniosMinimos))
                .map(EscalaArraigoEtec::getPorcentaje)
                .orElse(BigDecimal.ZERO);
    }
}
