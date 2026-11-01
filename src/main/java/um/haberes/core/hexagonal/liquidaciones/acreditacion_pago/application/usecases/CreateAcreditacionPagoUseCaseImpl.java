package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.ports.in.CreateAcreditacionPagoUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.ports.out.AcreditacionPagoRepository;

@Component
@RequiredArgsConstructor
public class CreateAcreditacionPagoUseCaseImpl implements CreateAcreditacionPagoUseCase {

    private final AcreditacionPagoRepository acreditacionPagoRepository;

    @Override
    public AcreditacionPago createAcreditacionPago(AcreditacionPago acreditacionPago) {
        return acreditacionPagoRepository.create(acreditacionPago);
    }
}
