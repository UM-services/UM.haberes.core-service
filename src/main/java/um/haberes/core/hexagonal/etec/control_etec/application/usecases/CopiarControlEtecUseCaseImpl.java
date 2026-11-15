package um.haberes.core.hexagonal.etec.control_etec.application.usecases;

import org.springframework.stereotype.Component;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.etec.control_etec.application.exception.ControlEtecOrigenException;
import um.haberes.core.hexagonal.etec.control_etec.application.exception.ControlEtecYaExisteException;
import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.in.CopiarControlEtecUseCase;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.out.ControlEtecRepository;

@Component
@RequiredArgsConstructor
public class CopiarControlEtecUseCaseImpl implements CopiarControlEtecUseCase {

    private final ControlEtecRepository repository;

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

        return repository.save(ControlEtec.builder()
                .controlEtecId(null)
                .anho(anho)
                .mes(mes)
                .valorPuntoEtec(origen.getValorPuntoEtec())
                .montoFijoCargo(origen.getMontoFijoCargo())
                .porcentajePresentismo(origen.getPorcentajePresentismo())
                .porcentajeAdicionalCarrera(origen.getPorcentajeAdicionalCarrera())
                .garantia(origen.getGarantia())
                .horasAdicionalCargaHoraria(origen.getHorasAdicionalCargaHoraria())
                .valorHoraAdicionalCargaHoraria(origen.getValorHoraAdicionalCargaHoraria())
                .build());
    }
}
