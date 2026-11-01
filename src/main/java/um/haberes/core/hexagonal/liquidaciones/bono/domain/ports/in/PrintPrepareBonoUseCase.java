package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.AuditoriaBono;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;

public interface PrintPrepareBonoUseCase {

    BonoImpresion printPrepare(AuditoriaBono auditoria);
}
