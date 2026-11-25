package um.haberes.core.hexagonal.etec.control_etec.application.usecases;

import java.util.List;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.control_etec.application.exception.ControlEtecOrigenException;
import um.haberes.core.hexagonal.etec.control_etec.application.exception.ControlEtecYaExisteException;
import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.in.CopiarControlEtecUseCase;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.out.ControlEtecRepository;
import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;
import um.haberes.core.hexagonal.etec.hora_escala.domain.ports.out.EscalaHoraEtecRepository;

@Component
@RequiredArgsConstructor
public class CopiarControlEtecUseCaseImpl implements CopiarControlEtecUseCase {

    private final ControlEtecRepository repository;

    private final EscalaHoraEtecRepository escalaHoraEtecRepository;

    @Transactional
    @Override
    public ControlEtec copiar(Integer anho, Integer mes) {
        if (repository.existsByPeriodo(anho, mes)) {
            throw new ControlEtecYaExisteException(anho, mes);
        }
        int anhoOrigen = mes == 1 ? anho - 1 : anho;
        int mesOrigen = mes == 1 ? 12 : mes - 1;
        ControlEtec origen = repository.findByPeriodo(anhoOrigen, mesOrigen)
                .orElseThrow(() -> new ControlEtecOrigenException(anho, mes));

        ControlEtec guardado = repository.save(ControlEtec.builder()
                .controlEtecId(null)
                .anho(anho)
                .mes(mes)
                .valorPuntoEtec(origen.getValorPuntoEtec())
                .montoFijoCargo(origen.getMontoFijoCargo())
                .horasReferenciaCargo(origen.getHorasReferenciaCargo())
                .basicoMaestroGrado(origen.getBasicoMaestroGrado())
                .porcentajePresentismo(origen.getPorcentajePresentismo())
                .porcentajeAdicionalCarrera(origen.getPorcentajeAdicionalCarrera())
                .garantia(origen.getGarantia())
                .horasAdicionalCargaHoraria(origen.getHorasAdicionalCargaHoraria())
                .valorHoraAdicionalCargaHoraria(origen.getValorHoraAdicionalCargaHoraria())
                .build());
        copiarEscalaHora(origen.getControlEtecId(), guardado.getControlEtecId());
        return guardado;
    }

    /**
     * La escala de horas de clase es parte de la configuración del período: al copiar el control
     * se copian sus tramos, apuntando al control nuevo.
     */
    private void copiarEscalaHora(Long controlOrigenId, Long controlDestinoId) {
        if (controlOrigenId == null || controlDestinoId == null) {
            return;
        }
        List<EscalaHoraEtec> tramos = escalaHoraEtecRepository.findAllByControlEtecId(controlOrigenId);
        if (tramos.isEmpty()) {
            return;
        }
        escalaHoraEtecRepository.saveAll(tramos.stream()
                .map(tramo -> EscalaHoraEtec.builder()
                        .escalaHoraEtecId(null)
                        .controlEtecId(controlDestinoId)
                        .categoriaId(tramo.getCategoriaId())
                        .horasDesde(tramo.getHorasDesde())
                        .horasHasta(tramo.getHorasHasta())
                        .montoFijo(tramo.getMontoFijo())
                        .valorHora(tramo.getValorHora())
                        .build())
                .toList());
    }
}
