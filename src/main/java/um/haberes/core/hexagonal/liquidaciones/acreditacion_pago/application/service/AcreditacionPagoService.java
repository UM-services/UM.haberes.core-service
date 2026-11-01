package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.application.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.application.exception.AcreditacionPagoException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.ports.in.CreateAcreditacionPagoUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.ports.in.GetAcreditacionPagoByUniqueUseCase;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.ports.in.UpdateAcreditacionPagoUseCase;

@Service
@RequiredArgsConstructor
public class AcreditacionPagoService {

    private final GetAcreditacionPagoByUniqueUseCase getAcreditacionPagoByUniqueUseCase;
    private final CreateAcreditacionPagoUseCase createAcreditacionPagoUseCase;
    private final UpdateAcreditacionPagoUseCase updateAcreditacionPagoUseCase;

    public AcreditacionPago findByUnique(Integer anho, Integer mes, OffsetDateTime fechaPago) {
        return getAcreditacionPagoByUniqueUseCase.getAcreditacionPagoByUnique(anho, mes, fechaPago)
                .orElseThrow(() -> new AcreditacionPagoException(anho, mes, fechaPago));
    }

    public AcreditacionPago add(AcreditacionPago acreditacionPago) {
        return createAcreditacionPagoUseCase.createAcreditacionPago(acreditacionPago);
    }

    public AcreditacionPago update(AcreditacionPago acreditacionPago, Long acreditacionPagoId) {
        return updateAcreditacionPagoUseCase.updateAcreditacionPago(acreditacionPagoId, acreditacionPago)
                .orElseThrow(() -> new AcreditacionPagoException(acreditacionPagoId));
    }
}
