package um.haberes.core.hexagonal.liquidaciones.bono.infrastructure.adapter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.exception.ControlException;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.model.BonoLote;
import um.haberes.core.hexagonal.liquidaciones.bono.domain.ports.out.BonoLoteDataPort;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.entity.CargoLiquidacionEntity;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.infrastructure.persistence.repository.JpaCargoLiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.codigo.application.exception.CodigoException;
import um.haberes.core.hexagonal.liquidaciones.codigo.application.service.CodigoService;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.persistence.entity.ItemEntity;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.persistence.repository.JpaItemRepository;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.persistence.entity.CursoCargoEntity;
import um.haberes.core.hexagonal.cursos.curso_cargo.infrastructure.persistence.repository.JpaCursoCargoRepository;
import um.haberes.core.hexagonal.personas.dependencia.application.service.DependenciaService;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.repository.JpaPersonaRepository;
import um.haberes.core.model.AntiguedadEntity;
import um.haberes.core.model.CargoClaseDetalleEntity;
import um.haberes.core.model.CodigoGrupoEntity;
import um.haberes.core.model.ContactoEntity;
import um.haberes.core.model.ControlEntity;
import um.haberes.core.model.LegajoBancoEntity;
import um.haberes.core.model.LegajoControlEntity;
import um.haberes.core.model.LiquidacionAdicionalEntity;
import um.haberes.core.repository.JpaAntiguedadRepository;
import um.haberes.core.repository.JpaCargoClaseDetalleRepository;
import um.haberes.core.repository.JpaContactoRepository;
import um.haberes.core.repository.JpaLegajoBancoRepository;
import um.haberes.core.repository.JpaLegajoControlRepository;
import um.haberes.core.repository.JpaLiquidacionAdicionalRepository;
import um.haberes.core.service.CodigoGrupoService;
import um.haberes.core.service.ControlService;
import um.haberes.core.service.facade.DesignacionToolService;
import um.haberes.core.service.facade.LiquidacionEtecService;

/**
 * Lee de una sola vez todo el lote de bonos de una dependencia.
 *
 * <p>Antes el reporte pedía por HTTP cada agregado de cada legajo (persona, antigüedad, items,
 * cursos, cargos, clases, adicionales, banco, contacto y control) y repetía los catálogos en
 * todas las llamadas. Acá se resuelve con consultas {@code IN} por agregado y se arman los
 * catálogos una única vez. Es un adaptador de lectura: no escribe nada.
 */
@Component
@RequiredArgsConstructor
public class BonoLoteJpaAdapter implements BonoLoteDataPort {

    private static final Integer CODIGO_981 = 981;
    private static final Integer FACULTAD_ETEC = 15;
    private static final Integer GEOGRAFICA_ETEC = 1;
    private static final Integer NIVEL_ETEC = 2;
    private static final Byte ACTIVO = 1;

    /** El mismo orden que aplica el flujo legajo por legajo a los cursos. */
    private static final Sort CURSO_LEGAJO_ORDER = Sort.by("curso.facultadId").ascending()
            .and(Sort.by("curso.geograficaId").ascending())
            .and(Sort.by("cargoTipoId").ascending())
            .and(Sort.by("curso.nombre").ascending());

    private final JpaPersonaRepository personaRepository;
    private final JpaAntiguedadRepository antiguedadRepository;
    private final JpaItemRepository itemRepository;
    private final JpaCursoCargoRepository cursoCargoRepository;
    private final JpaCargoLiquidacionRepository cargoLiquidacionRepository;
    private final JpaCargoClaseDetalleRepository cargoClaseDetalleRepository;
    private final JpaLiquidacionAdicionalRepository liquidacionAdicionalRepository;
    private final JpaLegajoBancoRepository legajoBancoRepository;
    private final JpaLegajoControlRepository legajoControlRepository;
    private final JpaContactoRepository contactoRepository;

    private final ControlService controlService;
    private final DependenciaService dependenciaService;
    private final CodigoService codigoService;
    private final CodigoGrupoService codigoGrupoService;
    private final DesignacionToolService designacionToolService;
    private final LiquidacionEtecService liquidacionEtecService;

    @Override
    public BonoLote cargar(List<Long> legajoIds, Integer anho, Integer mes, Integer dependenciaId,
                           String salida) {
        if (legajoIds == null || legajoIds.isEmpty()) {
            return new BonoLote(anho, mes, dependenciaId, salida, control(anho, mes), catalogo(),
                    List.of());
        }

        Map<Long, PersonaEntity> personas = personaRepository
                .findAllByLegajoIdIn(legajoIds, Sort.unsorted()).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(PersonaEntity::getLegajoId, persona -> persona, (a, b) -> a));
        Map<Long, AntiguedadEntity> antiguedades = antiguedadRepository
                .findAllByLegajoIdInAndAnhoAndMes(legajoIds, anho, mes).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(AntiguedadEntity::getLegajoId, antiguedad -> antiguedad,
                        (a, b) -> a));
        Map<Long, List<ItemEntity>> items = group(itemRepository
                .findAllByLegajoIdInAndAnhoAndMes(legajoIds, anho, mes), ItemEntity::getLegajoId);
        Map<Long, List<CursoCargoEntity>> cursos = group(cursoCargoRepository
                .findAllByLegajoIdInAndAnhoAndMes(legajoIds, anho, mes, CURSO_LEGAJO_ORDER),
                CursoCargoEntity::getLegajoId);
        Map<Long, List<CursoCargoEntity>> cursosEtec = group(cursoCargoRepository
                .findAllByLegajoIdInAndAnhoAndMesAndCursoNivelId(legajoIds, anho, mes, NIVEL_ETEC,
                        CURSO_LEGAJO_ORDER),
                CursoCargoEntity::getLegajoId);
        Map<Long, List<CargoLiquidacionEntity>> cargos = group(cargoLiquidacionRepository
                .findAllByLegajoIdInAndAnhoAndMes(legajoIds, anho, mes),
                CargoLiquidacionEntity::getLegajoId);
        Map<Long, List<CargoClaseDetalleEntity>> clases = group(cargoClaseDetalleRepository
                .findAllByLegajoIdInAndAnhoAndMes(legajoIds, anho, mes),
                CargoClaseDetalleEntity::getLegajoId);
        Map<Long, List<LiquidacionAdicionalEntity>> adicionales = group(
                liquidacionAdicionalRepository.findAllByLegajoIdInAndAnhoAndMes(legajoIds, anho, mes),
                LiquidacionAdicionalEntity::getLegajoId);
        Map<Long, LegajoBancoEntity> bancos = legajoBancoRepository
                .findAllByLegajoIdInAndAnhoAndMesAndResto(legajoIds, anho, mes, ACTIVO).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(LegajoBancoEntity::getLegajoId, banco -> banco, (a, b) -> a));
        Map<Long, LegajoControlEntity> controles = legajoControlRepository
                .findAllByLegajoIdInAndAnhoAndMes(legajoIds, anho, mes).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(LegajoControlEntity::getLegajoId, control -> control,
                        (a, b) -> a));
        Map<Long, ContactoEntity> contactos = contactoRepository.findAllByLegajoIdIn(legajoIds).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(ContactoEntity::getLegajoId, contacto -> contacto, (a, b) -> a));

        List<BonoLote.Legajo> legajos = new ArrayList<>(legajoIds.size());
        for (Long legajoId : legajoIds) {
            LegajoControlEntity legajoControl = controles.get(legajoId);
            ContactoEntity contacto = contactos.get(legajoId);
            legajos.add(new BonoLote.Legajo(legajoId,
                    persona(personas.get(legajoId)),
                    antiguedad(antiguedades.get(legajoId)),
                    indices(legajoId, anho, mes),
                    bancos.get(legajoId) == null ? null : bancos.get(legajoId).getCbu(),
                    items.getOrDefault(legajoId, List.of()).stream()
                            .map(item -> new BonoLote.Item(item.getCodigoId(), item.getImporte()))
                            .toList(),
                    cursos.getOrDefault(legajoId, List.of()).stream().map(this::curso).toList(),
                    cursosEtec.getOrDefault(legajoId, List.of()).stream().map(this::curso).toList(),
                    cargos.getOrDefault(legajoId, List.of()).stream().filter(this::docente)
                            .map(this::cargo).toList(),
                    cargos.getOrDefault(legajoId, List.of()).stream().filter(this::noDocente)
                            .map(this::cargo).toList(),
                    clases.getOrDefault(legajoId, List.of()).stream().map(this::clase).toList(),
                    adicionales.getOrDefault(legajoId, List.of()).stream()
                            .map(adicional -> new BonoLote.Adicional(adicional.getDependenciaId(),
                                    adicional.getAdicional()))
                            .toList(),
                    porcentajeAntiguedadEtec(legajoId, anho, mes),
                    contacto == null ? null : contacto.getMailInstitucional(),
                    legajoControl == null ? null : legajoControl.getLegajoControlId(),
                    legajoControl != null && valor(legajoControl.getBonoEnviado()) == ACTIVO));
        }
        return new BonoLote(anho, mes, dependenciaId, salida, control(anho, mes), catalogo(), legajos);
    }

    private BonoLote.Control control(Integer anho, Integer mes) {
        ControlEntity control;
        try {
            control = controlService.findByPeriodo(anho, mes);
        } catch (ControlException e) {
            return null;
        }
        return new BonoLote.Control(control.getFechaDesde(), control.getFechaHasta(),
                control.getFechaPago(), control.getFechaDeposito(), control.getAporteJubilatorio(),
                control.getHoraReferenciaEtec());
    }

    private BonoLote.Catalogo catalogo() {
        List<BonoLote.Dependencia> dependencias = dependenciaService.findAll().stream()
                .filter(Objects::nonNull)
                .filter(dependencia -> dependencia.getDependenciaId() != null)
                .map(dependencia -> new BonoLote.Dependencia(dependencia.getDependenciaId(),
                        dependencia.getAcronimo()))
                .toList();
        return new BonoLote.Catalogo(dependencias, codigo981(),
                grupos(codigoGrupoService.findAllByRemunerativo(ACTIVO)),
                grupos(codigoGrupoService.findAllByNoRemunerativo(ACTIVO)),
                grupos(codigoGrupoService.findAllByDeduccion(ACTIVO)));
    }

    private BonoLote.Codigo codigo981() {
        try {
            Codigo codigo = codigoService.findByCodigoId(CODIGO_981);
            return codigo == null ? null : new BonoLote.Codigo(codigo.getCodigoId(), codigo.getNombre());
        } catch (CodigoException e) {
            return null;
        }
    }

    private List<BonoLote.Grupo> grupos(List<CodigoGrupoEntity> grupos) {
        if (grupos == null) {
            return List.of();
        }
        return grupos.stream()
                .filter(Objects::nonNull)
                .map(grupo -> new BonoLote.Grupo(
                        grupo.getCodigoId() != null ? grupo.getCodigoId()
                                : grupo.getCodigo() == null ? null : grupo.getCodigo().getCodigoId(),
                        grupo.getCodigo() == null ? null : grupo.getCodigo().getNombre()))
                .toList();
    }

    private BonoLote.Persona persona(PersonaEntity persona) {
        if (persona == null) {
            return null;
        }
        return new BonoLote.Persona(persona.getLegajoId(), persona.getApellido(), persona.getNombre(),
                persona.getDocumento() == null ? null : persona.getDocumento().toString(),
                persona.getCuil(), persona.getDependenciaId(), persona.getAltaAdministrativa(),
                valor(persona.getDirectivoEtec()));
    }

    private BonoLote.Antiguedad antiguedad(AntiguedadEntity antiguedad) {
        if (antiguedad == null) {
            return null;
        }
        return new BonoLote.Antiguedad(antiguedad.getMesesDocentes(),
                antiguedad.getMesesAdministrativos());
    }

    private List<BigDecimal> indices(Long legajoId, Integer anho, Integer mes) {
        try {
            List<BigDecimal> indices = designacionToolService.indiceAntiguedad(legajoId, anho, mes);
            return indices == null ? List.of() : indices;
        } catch (RuntimeException e) {
            return List.of();
        }
    }

    private BigDecimal porcentajeAntiguedadEtec(Long legajoId, Integer anho, Integer mes) {
        try {
            return liquidacionEtecService.calcularPorcentajeAntiguedad(legajoId, anho, mes,
                    FACULTAD_ETEC, GEOGRAFICA_ETEC);
        } catch (RuntimeException e) {
            return BigDecimal.ZERO;
        }
    }

    private boolean docente(CargoLiquidacionEntity cargo) {
        return cargo.getCategoria() != null && valor(cargo.getCategoria().getDocente()) == ACTIVO;
    }

    private boolean noDocente(CargoLiquidacionEntity cargo) {
        return cargo.getCategoria() != null && valor(cargo.getCategoria().getNoDocente()) == ACTIVO;
    }

    private BonoLote.CursoCargo curso(CursoCargoEntity curso) {
        return new BonoLote.CursoCargo(
                curso.getCargoTipo() == null ? null : curso.getCargoTipo().getNombre(),
                curso.getCurso() == null ? null : curso.getCurso().getNombre(),
                valor(curso.getDesarraigo()),
                curso.getCurso() == null ? 0 : valor(curso.getCurso().getAnual()),
                curso.getCurso() == null ? 0 : valor(curso.getCurso().getSemestre1()),
                curso.getCurso() == null ? 0 : valor(curso.getCurso().getSemestre2()),
                curso.getHorasSemanales());
    }

    private BonoLote.CargoLiquidado cargo(CargoLiquidacionEntity cargo) {
        return new BonoLote.CargoLiquidado(
                cargo.getDependencia() == null ? null : cargo.getDependencia().getAcronimo(),
                cargo.getCategoria() == null ? cargo.getCategoriaNombre()
                        : cargo.getCategoria().getNombre(),
                cargo.getCategoriaBasico(), cargo.getHorasJornada(), cargo.getJornada());
    }

    private BonoLote.Clase clase(CargoClaseDetalleEntity detalle) {
        return new BonoLote.Clase(
                detalle.getDependencia() == null ? null : detalle.getDependencia().getAcronimo(),
                detalle.getCargoClase() == null || detalle.getCargoClase().getClase() == null ? null
                        : detalle.getCargoClase().getClase().getNombre(),
                detalle.getCargoClase() == null ? null : detalle.getCargoClase().getNombre(),
                detalle.getCargoClasePeriodo() == null ? null
                        : detalle.getCargoClasePeriodo().getDescripcion(),
                detalle.getCargoClasePeriodo() == null
                        || detalle.getCargoClasePeriodo().getGeografica() == null ? null
                        : detalle.getCargoClasePeriodo().getGeografica().getNombre(),
                detalle.getCargoClasePeriodo() == null ? 0
                        : detalle.getCargoClasePeriodo().getHoras(),
                detalle.getValorHora());
    }

    private <T> Map<Long, List<T>> group(List<T> valores, java.util.function.Function<T, Long> key) {
        if (valores == null) {
            return Map.of();
        }
        return valores.stream()
                .filter(Objects::nonNull)
                .filter(valor -> key.apply(valor) != null)
                .collect(Collectors.groupingBy(key));
    }

    private static byte valor(Byte valor) {
        return valor == null ? 0 : valor;
    }
}
