package um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.general.domain.model.InicioProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.PadronLiquidacionGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ResultadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.dto.InicioProcesoGeneralResponse;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.dto.PadronLiquidacionGeneralResponse;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.dto.ProcesoGeneralResponse;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.web.dto.ResultadoLegajoGeneralResponse;

/** Mapeo del dominio de liquidación general a los contratos REST. */
@Component
public class LiquidacionGeneralDtoMapper {

    public PadronLiquidacionGeneralResponse toResponse(PadronLiquidacionGeneral padron) {
        return new PadronLiquidacionGeneralResponse(padron.anho(), padron.mes(),
                new PadronLiquidacionGeneralResponse.Acciones(padron.acciones().hayControl(),
                        padron.acciones().puedeGenerarCargos(), padron.acciones().puedeLiquidar(),
                        padron.acciones().puedeEliminar()),
                new PadronLiquidacionGeneralResponse.Resumen(padron.resumen().total(),
                        padron.resumen().docentes(), padron.resumen().noDocentes(),
                        padron.resumen().conNovedades(), padron.resumen().liquidados(),
                        padron.resumen().bloqueados(), padron.resumen().netoNegativo(),
                        padron.resumen().netoCero(), padron.resumen().totalNeto()),
                padron.filas().stream()
                        .map(fila -> new PadronLiquidacionGeneralResponse.Fila(fila.orden(), fila.legajoId(),
                                fila.apellido(), fila.nombre(), fila.estado(), fila.liquida(),
                                fila.dependenciaId(), fila.salida(), fila.esDocente(), fila.esNoDocente(),
                                fila.tieneNovedades(), fila.liquidado(), fila.bloqueado(),
                                fila.totalRemunerativo(), fila.totalNoRemunerativo(), fila.totalDeduccion(),
                                fila.totalNeto()))
                        .toList());
    }

    public ProcesoGeneralResponse toResponse(ProcesoGeneral proceso) {
        return new ProcesoGeneralResponse(proceso.procesoId(), proceso.tipo().name(), proceso.anho(),
                proceso.mes(), proceso.estado().name(), proceso.total(), proceso.procesados(),
                proceso.progreso(), proceso.exitosos(), proceso.omitidos(), proceso.fallidos(),
                proceso.forzar(), proceso.iniciadoPor(), proceso.iniciadoEn(), proceso.finalizadoEn(),
                proceso.mensaje());
    }

    public InicioProcesoGeneralResponse toResponse(InicioProcesoGeneral inicio) {
        return new InicioProcesoGeneralResponse(inicio.iniciadoProceso(),
                inicio.proceso() == null ? null : toResponse(inicio.proceso()), inicio.motivo(),
                inicio.mensaje());
    }

    public ResultadoLegajoGeneralResponse toResponse(ResultadoLegajoGeneral resultado) {
        return new ResultadoLegajoGeneralResponse(resultado.detalleId(), resultado.legajoId(),
                resultado.apellido(), resultado.nombre(),
                resultado.resultado() == null ? null : resultado.resultado().name(),
                resultado.motivo() == null ? null : resultado.motivo().name(), resultado.detalle(),
                resultado.totalNeto(), resultado.registradoEn());
    }
}
