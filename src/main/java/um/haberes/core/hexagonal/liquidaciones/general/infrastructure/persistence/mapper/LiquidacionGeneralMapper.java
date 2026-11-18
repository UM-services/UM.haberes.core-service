package um.haberes.core.hexagonal.liquidaciones.general.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ProcesoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.domain.model.ResultadoLegajoGeneral;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.persistence.entity.LiquidacionGeneralDetalleEntity;
import um.haberes.core.hexagonal.liquidaciones.general.infrastructure.persistence.entity.LiquidacionGeneralProcesoEntity;

/** Mapeo entre el dominio de procesos masivos y sus entidades JPA. */
@Component
public class LiquidacionGeneralMapper {

    public LiquidacionGeneralProcesoEntity toEntity(ProcesoGeneral proceso) {
        if (proceso == null) {
            return null;
        }
        return new LiquidacionGeneralProcesoEntity(proceso.procesoId(), proceso.tipo(), proceso.anho(),
                proceso.mes(), proceso.estado(), proceso.total(), proceso.procesados(), proceso.exitosos(),
                proceso.omitidos(), proceso.fallidos(), proceso.forzar(), proceso.iniciadoPor(),
                proceso.iniciadoEn(), proceso.finalizadoEn(), proceso.mensaje());
    }

    public ProcesoGeneral toDomain(LiquidacionGeneralProcesoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ProcesoGeneral(entity.getProcesoId(), entity.getTipo(), entity.getAnho(), entity.getMes(),
                entity.getEstado(), entity.getTotal(), entity.getProcesados(), entity.getExitosos(),
                entity.getOmitidos(), entity.getFallidos(), entity.isForzar(), entity.getIniciadoPor(),
                entity.getIniciadoEn(), entity.getFinalizadoEn(), entity.getMensaje());
    }

    public LiquidacionGeneralDetalleEntity toEntity(String procesoId, ResultadoLegajoGeneral resultado) {
        if (resultado == null) {
            return null;
        }
        return new LiquidacionGeneralDetalleEntity(resultado.detalleId(), procesoId, resultado.legajoId(),
                resultado.apellido(), resultado.nombre(), resultado.resultado(), resultado.motivo(),
                resultado.detalle(), resultado.totalNeto(), resultado.registradoEn());
    }

    public ResultadoLegajoGeneral toDomain(LiquidacionGeneralDetalleEntity entity) {
        if (entity == null) {
            return null;
        }
        return new ResultadoLegajoGeneral(entity.getDetalleId(), entity.getLegajoId(), entity.getApellido(),
                entity.getNombre(), entity.getResultado(), entity.getMotivo(), entity.getDetalle(),
                entity.getTotalNeto(), entity.getRegistradoEn());
    }
}
