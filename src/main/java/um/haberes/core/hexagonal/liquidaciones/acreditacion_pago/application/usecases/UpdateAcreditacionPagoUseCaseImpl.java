package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.application.usecases;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.ports.in.UpdateAcreditacionPagoUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.ports.out.AcreditacionPagoRepository;

@Component
@RequiredArgsConstructor
public class UpdateAcreditacionPagoUseCaseImpl implements UpdateAcreditacionPagoUseCase {

    private final AcreditacionPagoRepository acreditacionPagoRepository;

    @Override
    public Optional<AcreditacionPago> updateAcreditacionPago(Long acreditacionPagoId, AcreditacionPago acreditacionPago) {
        return acreditacionPagoRepository.update(acreditacionPagoId, acreditacionPago);
    }
}
