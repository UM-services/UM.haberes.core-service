package um.haberes.core.hexagonal.liquidaciones.bono.application.usecases;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.bono.application.exception.BonoException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.AuditoriaBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.EnvioBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.PrepararEnvioBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in.RegistrarAuditoriaBonoUseCase;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.ContactoRepository;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.MailValidator;

@Component
@RequiredArgsConstructor
public class PrepararEnvioBonoUseCaseImpl implements PrepararEnvioBonoUseCase {

    private final MailValidator mailValidator;
    private final ContactoRepository contactoRepository;
    private final RegistrarAuditoriaBonoUseCase registrarAuditoriaBonoUseCase;

    @Override
    @Transactional
    public BonoImpresion prepararEnvio(EnvioBono envio) {
        if (envio.getMailInstitucional() == null || !mailValidator.isValid(envio.getMailInstitucional())) {
            throw new BonoException("Cannot send Bono: mail institucional invalido");
        }
        contactoRepository.upsertMailInstitucional(envio.getLegajoId(), envio.getMailInstitucional());
        AuditoriaBono auditoria = AuditoriaBono.builder()
                .legajoId(envio.getLegajoId())
                .anho(envio.getAnho())
                .mes(envio.getMes())
                .legajoIdSolicitud(envio.getLegajoIdSolicitud())
                .ipAddress(envio.getIpAddress())
                .build();
        return registrarAuditoriaBonoUseCase.registrarAuditoria(auditoria);
    }
}
