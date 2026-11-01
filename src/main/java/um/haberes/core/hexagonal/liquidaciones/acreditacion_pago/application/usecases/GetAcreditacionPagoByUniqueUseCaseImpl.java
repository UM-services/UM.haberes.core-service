package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.application.usecases;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.ports.in.GetAcreditacionPagoByUniqueUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.ports.out.AcreditacionPagoRepository;

@Component
@RequiredArgsConstructor
public class GetAcreditacionPagoByUniqueUseCaseImpl implements GetAcreditacionPagoByUniqueUseCase {

    private final AcreditacionPagoRepository acreditacionPagoRepository;

    @Override
    public Optional<AcreditacionPago> getAcreditacionPagoByUnique(Integer anho, Integer mes, OffsetDateTime fechaPago) {
        return acreditacionPagoRepository.findByAnhoAndMesAndFechaPago(anho, mes, fechaPago);
    }
}
