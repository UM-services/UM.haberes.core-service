package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.ResumenPreparacionBonos;
import um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.web.dto.BonoDependenciaPrintPrepareResponse;

@Component
public class BonoDependenciaDtoMapper {

    public BonoDependenciaPrintPrepareResponse toPrintPrepareResponse(
            ResumenPreparacionBonos resumen) {
        if (resumen == null) {
            return null;
        }
        List<BonoDependenciaPrintPrepareResponse.Fallido> fallidos = resumen.fallidos().stream()
                .map(fallido -> new BonoDependenciaPrintPrepareResponse.Fallido(
                        fallido.legajoId(), fallido.motivo()))
                .toList();
        return BonoDependenciaPrintPrepareResponse.builder()
                .dependenciaId(resumen.dependenciaId())
                .anho(resumen.anho())
                .mes(resumen.mes())
                .salida(resumen.salida())
                .total(resumen.total())
                .preparados(resumen.preparados())
                .fallidos(fallidos)
                .build();
    }
}
