package um.haberes.core.hexagonal.liquidaciones.bono.application.usecases;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.application.exception.BonoException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.AuditoriaBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.RegistrarAuditoriaBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.BonoImpresionRepository;

@Component
@RequiredArgsConstructor
public class RegistrarAuditoriaBonoUseCaseImpl implements RegistrarAuditoriaBonoUseCase {

    private final BonoImpresionRepository bonoImpresionRepository;

    @Override
    public BonoImpresion registrarAuditoria(AuditoriaBono auditoria) {
        if (auditoria.getLegajoIdSolicitud() == null) {
            throw new BonoException(
                    "Cannot register Bono audit: legajoIdSolicitud is required (header X-Legajo-Solicitante or body)");
        }
        BonoImpresion impresion = BonoImpresion.builder()
                .legajoId(auditoria.getLegajoId())
                .anho(auditoria.getAnho())
                .mes(auditoria.getMes())
                .legajoIdSolicitud(auditoria.getLegajoIdSolicitud())
                .ipAddress(auditoria.getIpAddress() == null ? "" : auditoria.getIpAddress())
                .build();
        return bonoImpresionRepository.save(impresion);
    }
}
