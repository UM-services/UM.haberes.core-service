package um.haberes.core.hexagonal.etec.hora_escala.application.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;
import um.haberes.core.hexagonal.etec.hora_escala.domain.ports.in.GetEscalaHoraEtecUseCase;
import um.haberes.core.hexagonal.etec.hora_escala.domain.ports.in.ReplaceEscalaHoraEtecUseCase;

/**
 * Escala de horas de clase ETEC del período. El motor la consulta con las horas semanales de los
 * cursos ETEC de una sede y usa el tramo que contiene ese total.
 */
@Service
@RequiredArgsConstructor
public class EscalaHoraEtecService {

    private final GetEscalaHoraEtecUseCase getEscalaHoraEtecUseCase;

    private final ReplaceEscalaHoraEtecUseCase replaceEscalaHoraEtecUseCase;

    public List<EscalaHoraEtec> findByPeriodo(Integer anho, Integer mes) {
        return getEscalaHoraEtecUseCase.findByPeriodo(anho, mes);
    }

    public List<EscalaHoraEtec> reemplazar(Integer anho, Integer mes, List<EscalaHoraEtec> tramos) {
        return replaceEscalaHoraEtecUseCase.reemplazar(anho, mes, tramos);
    }

    /** Tramo que contiene las horas indicadas; vacío si las horas no caen en ningún tramo. */
    public Optional<EscalaHoraEtec> tramoParaHoras(List<EscalaHoraEtec> tramos, BigDecimal horas) {
        if (tramos == null || horas == null) {
            return Optional.empty();
        }
        return tramos.stream()
                .filter(tramo -> tramo.contiene(horas))
                .findFirst();
    }
}
