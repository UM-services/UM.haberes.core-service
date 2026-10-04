package um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.in;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoImpresion;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.EnvioBono;

public interface PrepararEnvioBonoUseCase {

    BonoImpresion prepararEnvio(EnvioBono envio);
}
