package um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.persistence.adapter;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.model.AcreditacionPago;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.domain.ports.out.AcreditacionPagoRepository;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.persistence.entity.AcreditacionPagoEntity;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.persistence.mapper.AcreditacionPagoMapper;
import um.haberes.core.hexagonal.liquidaciones.acreditacion_pago.infrastructure.persistence.repository.JpaAcreditacionPagoRepository;

@Component
@RequiredArgsConstructor
public class JpaAcreditacionPagoRepositoryAdapter implements AcreditacionPagoRepository {

    private final JpaAcreditacionPagoRepository jpaAcreditacionPagoRepository;
    private final AcreditacionPagoMapper acreditacionPagoMapper;

    @Override
    public Optional<AcreditacionPago> findByAnhoAndMesAndFechaPago(Integer anho, Integer mes, OffsetDateTime fechaPago) {
        return jpaAcreditacionPagoRepository.findByAnhoAndMesAndFechaPago(anho, mes, fechaPago)
                .map(acreditacionPagoMapper::toDomain);
    }

    @Override
    public AcreditacionPago create(AcreditacionPago acreditacionPago) {
        AcreditacionPagoEntity entity = acreditacionPagoMapper.toEntity(acreditacionPago);
        return acreditacionPagoMapper.toDomain(jpaAcreditacionPagoRepository.save(entity));
    }

    @Override
    public Optional<AcreditacionPago> update(Long acreditacionPagoId, AcreditacionPago acreditacionPago) {
        AcreditacionPagoEntity mapped = acreditacionPagoMapper.toEntity(acreditacionPago);
        return jpaAcreditacionPagoRepository.findById(acreditacionPagoId)
                .map(entity -> {
                    entity.setAnho(mapped.getAnho());
                    entity.setMes(mapped.getMes());
                    entity.setFechaPago(mapped.getFechaPago());
                    entity.setTotalSantander(mapped.getTotalSantander());
                    entity.setTotalOtrosBancos(mapped.getTotalOtrosBancos());
                    entity.setComprobanteIdPago(mapped.getComprobanteIdPago());
                    entity.setPuntoVentaPago(mapped.getPuntoVentaPago());
                    entity.setNumeroComprobantePago(mapped.getNumeroComprobantePago());
                    return acreditacionPagoMapper.toDomain(jpaAcreditacionPagoRepository.save(entity));
                });
    }
}
