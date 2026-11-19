package um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import um.haberes.core.hexagonal.crossslice.cargo_liquidacion.model.CargoLiquidacionView;
import um.haberes.core.hexagonal.crossslice.cargos_clase.model.CargoConClaseView;
import um.haberes.core.hexagonal.crossslice.control.model.ControlView;
import um.haberes.core.hexagonal.crossslice.item.model.ItemView;
import um.haberes.core.hexagonal.crossslice.liquidacion.model.LiquidacionView;
import um.haberes.core.hexagonal.crossslice.novedad.model.NovedadView;
import um.haberes.core.hexagonal.crossslice.persona.model.PersonaView;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.FichaLiquidacionIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.NovedadRegistradaIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.RegistroNovedadIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResumenGeneracionCargos;
import um.haberes.core.hexagonal.liquidaciones.individual.domain.model.ResultadoProcesoIndividual;
import um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto.FichaLiquidacionIndividualResponse;
import um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto.NovedadRegistradaResponse;
import um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto.RegistrarNovedadIndividualRequest;
import um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto.ResumenGeneracionCargosResponse;
import um.haberes.core.hexagonal.liquidaciones.individual.infrastructure.web.dto.ResultadoProcesoResponse;

/** Traduce el dominio del slice a los DTO del contrato REST. */
@Component
public class LiquidacionIndividualDtoMapper {

    public FichaLiquidacionIndividualResponse toResponse(FichaLiquidacionIndividual ficha) {
        if (ficha == null) {
            return null;
        }
        return new FichaLiquidacionIndividualResponse(
                ficha.legajoId(), ficha.anho(), ficha.mes(),
                toResponse(ficha.persona()),
                toResponse(ficha.control()),
                toResponse(ficha.liquidacion()),
                toResponse(ficha.antiguedad()),
                toCargoList(ficha.cargosDocentes()),
                toCargoList(ficha.cargosNoDocentes()),
                toCargoConClaseList(ficha.cargosConClase()),
                toNovedadList(ficha.novedades()),
                toItemList(ficha.items()),
                new FichaLiquidacionIndividualResponse.Acciones(
                        ficha.acciones().puedeGenerarCargos(),
                        ficha.acciones().puedeLiquidar(),
                        ficha.acciones().puedeEliminarLiquidacion(),
                        ficha.acciones().puedeEditarNovedades(),
                        ficha.acciones().bloqueado(),
                        ficha.acciones().motivoBloqueo()));
    }

    public ResultadoProcesoResponse toResponse(ResultadoProcesoIndividual resultado) {
        if (resultado == null) {
            return null;
        }
        return new ResultadoProcesoResponse(resultado.resultado().name(), resultado.mensaje());
    }

    public ResumenGeneracionCargosResponse toResponse(ResumenGeneracionCargos resumen) {
        if (resumen == null) {
            return null;
        }
        return new ResumenGeneracionCargosResponse(resumen.resultado().name(), resumen.mensaje(),
                resumen.docentes(), resumen.noDocentes());
    }

    public NovedadRegistradaResponse toResponse(NovedadRegistradaIndividual registrada) {
        if (registrada == null) {
            return null;
        }
        return new NovedadRegistradaResponse(registrada.resultado().name(), registrada.mensaje(),
                toResponse(registrada.novedad()));
    }

    public RegistroNovedadIndividual toDomain(RegistrarNovedadIndividualRequest request) {
        if (request == null) {
            return null;
        }
        return new RegistroNovedadIndividual(request.getCodigoId(), request.getImporte(), request.getValor(),
                request.getObservaciones(), Boolean.TRUE.equals(request.getForzar()));
    }

    private FichaLiquidacionIndividualResponse.Persona toResponse(PersonaView persona) {
        if (persona == null) {
            return null;
        }
        return new FichaLiquidacionIndividualResponse.Persona(persona.legajoId(), persona.documento(),
                persona.apellido(), persona.nombre(), persona.estado(), persona.liquida(),
                persona.dependenciaId(), persona.salida());
    }

    private FichaLiquidacionIndividualResponse.Control toResponse(ControlView control) {
        if (control == null) {
            return null;
        }
        return new FichaLiquidacionIndividualResponse.Control(control.controlId(), control.anho(),
                control.mes(), control.modoLiquidacionId());
    }

    private FichaLiquidacionIndividualResponse.Liquidacion toResponse(LiquidacionView liquidacion) {
        if (liquidacion == null) {
            return null;
        }
        return new FichaLiquidacionIndividualResponse.Liquidacion(liquidacion.liquidacionId(),
                liquidacion.bloqueado(), liquidacion.estado(), liquidacion.liquida(),
                liquidacion.totalRemunerativo(), liquidacion.totalNoRemunerativo(),
                liquidacion.totalDeduccion(), liquidacion.totalNeto(), liquidacion.fechaLiquidacion(),
                liquidacion.fechaAcreditacion());
    }

    private FichaLiquidacionIndividualResponse.Antiguedad toResponse(
            um.haberes.core.hexagonal.crossslice.antiguedad.model.AntiguedadView antiguedad) {
        if (antiguedad == null) {
            return null;
        }
        return new FichaLiquidacionIndividualResponse.Antiguedad(antiguedad.mesesDocentes(),
                antiguedad.mesesAdministrativos(), antiguedad.indiceDocente(),
                antiguedad.indiceAdministrativo());
    }

    private List<FichaLiquidacionIndividualResponse.Cargo> toCargoList(List<CargoLiquidacionView> cargos) {
        return cargos.stream().map(this::toResponse).toList();
    }

    private FichaLiquidacionIndividualResponse.Cargo toResponse(CargoLiquidacionView cargo) {
        return new FichaLiquidacionIndividualResponse.Cargo(cargo.cargoLiquidacionId(), cargo.dependenciaId(),
                cargo.dependenciaAcronimo(), cargo.fechaDesde(), cargo.fechaHasta(), cargo.categoriaNombre(),
                cargo.categoriaBasico(), cargo.estadoDocente(), cargo.horasJornada(), cargo.jornada(),
                cargo.presentismo(), cargo.situacion());
    }

    private List<FichaLiquidacionIndividualResponse.CargoConClase> toCargoConClaseList(
            List<CargoConClaseView> cargos) {
        return cargos.stream().map(this::toResponse).toList();
    }

    private FichaLiquidacionIndividualResponse.CargoConClase toResponse(CargoConClaseView cargo) {
        return new FichaLiquidacionIndividualResponse.CargoConClase(cargo.cargoClaseDetalleId(),
                cargo.descripcion(), cargo.facultadReducido(), cargo.sedeNombre(), cargo.periodoDesde(),
                cargo.periodoHasta(), cargo.horas(), cargo.valorHora());
    }

    private List<FichaLiquidacionIndividualResponse.Novedad> toNovedadList(List<NovedadView> novedades) {
        return novedades.stream().map(this::toResponse).toList();
    }

    private FichaLiquidacionIndividualResponse.Novedad toResponse(NovedadView novedad) {
        if (novedad == null) {
            return null;
        }
        return new FichaLiquidacionIndividualResponse.Novedad(novedad.novedadId(), novedad.codigoId(),
                novedad.codigoNombre(), novedad.importe(), novedad.value(), novedad.observaciones());
    }

    private List<FichaLiquidacionIndividualResponse.Item> toItemList(List<ItemView> items) {
        return items.stream().map(this::toResponse).toList();
    }

    private FichaLiquidacionIndividualResponse.Item toResponse(ItemView item) {
        return new FichaLiquidacionIndividualResponse.Item(item.itemId(), item.codigoId(), item.codigoNombre(),
                item.importe());
    }
}
