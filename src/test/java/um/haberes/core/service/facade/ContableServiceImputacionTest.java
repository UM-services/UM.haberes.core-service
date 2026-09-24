package um.haberes.core.service.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import um.haberes.core.client.CuentaMovimientoClient;
import um.haberes.core.hexagonal.contabilidad.cargo_clase_imputacion.application.service.CargoClaseImputacionService;
import um.haberes.core.hexagonal.contabilidad.categoria_imputacion.application.service.CategoriaImputacionService;
import um.haberes.core.hexagonal.contabilidad.codigo_imputacion.application.service.CodigoImputacionService;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.application.service.LegajoCargoClaseImputacionService;
import um.haberes.core.hexagonal.contabilidad.legajo_cargo_clase_imputacion.domain.model.LegajoCargoClaseImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.application.service.LegajoCategoriaImputacionService;
import um.haberes.core.hexagonal.contabilidad.legajo_categoria_imputacion.domain.model.LegajoCategoriaImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.application.service.LegajoCodigoImputacionService;
import um.haberes.core.hexagonal.contabilidad.legajo_codigo_imputacion.domain.model.LegajoCodigoImputacion;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.application.exception.LegajoContabilidadException;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.application.service.LegajoContabilidadService;
import um.haberes.core.hexagonal.contabilidad.legajo_contabilidad.domain.model.LegajoContabilidad;
import um.haberes.core.hexagonal.facultad.application.service.FacultadService;
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.hexagonal.geografica.application.service.GeograficaService;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.service.CargoLiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.categoria.application.service.CategoriaService;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.codigo.application.service.CodigoService;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.item.application.service.ItemService;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.service.LiquidacionService;
import um.haberes.core.hexagonal.personas.dependencia.application.service.DependenciaService;
import um.haberes.core.hexagonal.personas.dependencia.domain.model.Dependencia;
import um.haberes.core.model.CargoClaseEntity;
import um.haberes.core.model.CodigoGrupoEntity;
import um.haberes.core.model.dto.imputacion.ImputacionIndividualResponse;
import um.haberes.core.service.*;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContableServiceImputacionTest {

    @Mock private LegajoCargoClaseImputacionService legajoCargoClaseImputacionService;
    @Mock private LegajoCategoriaImputacionService legajoCategoriaImputacionService;
    @Mock private LegajoCodigoImputacionService legajoCodigoImputacionService;
    @Mock private ItemService itemService;
    @Mock private DesignacionToolService designacionToolService;
    @Mock private LiquidacionService liquidacionService;
    @Mock private CargoLiquidacionService cargoLiquidacionService;
    @Mock private CategoriaImputacionService categoriaImputacionService;
    @Mock private CargoClaseDetalleService cargoClaseDetalleService;
    @Mock private CargoClaseImputacionService cargoClaseImputacionService;
    @Mock private ActividadService actividadService;
    @Mock private CodigoService codigoService;
    @Mock private CodigoImputacionService codigoImputacionService;
    @Mock private LegajoContabilidadService legajoContabilidadService;
    @Mock private CodigoGrupoService codigoGrupoService;
    @Mock private LiquidacionAdicionalService liquidacionAdicionalService;
    @Mock private CuentaMovimientoClient cuentaMovimientoClient;
    @Mock private CategoriaService categoriaService;
    @Mock private CargoClaseService cargoClaseService;
    @Mock private FacultadService facultadService;
    @Mock private GeograficaService geograficaService;
    @Mock private DependenciaService dependenciaService;

    private ContableService contableService;

    @BeforeEach
    void setUp() {
        contableService = new ContableService(
                legajoCargoClaseImputacionService,
                legajoCategoriaImputacionService,
                legajoCodigoImputacionService,
                itemService,
                designacionToolService,
                liquidacionService,
                cargoLiquidacionService,
                categoriaImputacionService,
                cargoClaseDetalleService,
                cargoClaseImputacionService,
                actividadService,
                codigoService,
                codigoImputacionService,
                legajoContabilidadService,
                codigoGrupoService,
                liquidacionAdicionalService,
                cuentaMovimientoClient,
                categoriaService,
                cargoClaseService,
                facultadService,
                geograficaService,
                dependenciaService
        );
    }

    @Test
    void getImputacionIndividual_calculatesTotalsAndResolvesCatalogsCorrectly() {
        Long legajoId = 1234L;
        Integer anho = 2026;
        Integer mes = 3;

        // Imputaciones
        LegajoCategoriaImputacion catImp = new LegajoCategoriaImputacion(
                1L, legajoId, anho, mes, 10, 1, 2, 100,
                new BigDecimal("210101"), new BigDecimal("50000.00"), new BigDecimal("10000.00"), BigDecimal.ZERO);
        when(legajoCategoriaImputacionService.findAllByLegajo(legajoId, anho, mes)).thenReturn(List.of(catImp));

        LegajoCargoClaseImputacion claseImp = new LegajoCargoClaseImputacion(
                2L, legajoId, anho, mes, 10, 1, 2, 200L,
                new BigDecimal("210102"), new BigDecimal("30000.00"), new BigDecimal("5000.00"), BigDecimal.ZERO);
        when(legajoCargoClaseImputacionService.findAllByLegajo(legajoId, anho, mes)).thenReturn(List.of(claseImp));

        LegajoCodigoImputacion codImpRemun = new LegajoCodigoImputacion(
                3L, legajoId, anho, mes, 10, 1, 2, 10,
                new BigDecimal("210103"), new BigDecimal("15000.00"), BigDecimal.ZERO);
        LegajoCodigoImputacion codImpNoRemun = new LegajoCodigoImputacion(
                4L, legajoId, anho, mes, 10, 1, 2, 50,
                new BigDecimal("210104"), new BigDecimal("7000.00"), BigDecimal.ZERO);
        when(legajoCodigoImputacionService.findAllByLegajo(legajoId, anho, mes))
                .thenReturn(List.of(codImpRemun, codImpNoRemun));

        // Catálogos
        Categoria cat = Categoria.builder().categoriaId(100).nombre("Administrativo Principal").build();
        when(categoriaService.findAll()).thenReturn(List.of(cat));

        CargoClaseEntity cargoClase = new CargoClaseEntity(200L, "Profesor Titular", null, null);
        when(cargoClaseService.findAll()).thenReturn(List.of(cargoClase));

        Codigo cod10 = Codigo.builder().codigoId(10).nombre("Básico Extra").build();
        Codigo cod50 = Codigo.builder().codigoId(50).nombre("Refrigerio").build();
        when(codigoService.findAll()).thenReturn(List.of(cod10, cod50));

        Facultad fac = Facultad.builder().facultadId(1).nombre("Facultad de Ingeniería").build();
        when(facultadService.getAllFacultades()).thenReturn(List.of(fac));

        Geografica geo = Geografica.builder().geograficaId(2).nombre("Sede Central").build();
        when(geograficaService.getAllGeograficas()).thenReturn(List.of(geo));

        Dependencia dep = Dependencia.builder().dependenciaId(10).acronimo("FI-CENTRAL").nombre("Facultad Ing").build();
        when(dependenciaService.findAll()).thenReturn(List.of(dep));

        // Grupos remunerativos (código 10 es remunerativo, 50 no lo es)
        CodigoGrupoEntity grupoRemun = new CodigoGrupoEntity();
        grupoRemun.setCodigoId(10);
        grupoRemun.setRemunerativo((byte) 1);
        when(codigoGrupoService.findAllByRemunerativo((byte) 1)).thenReturn(List.of(grupoRemun));

        // Control contable
        LegajoContabilidad contabilidad = LegajoContabilidad.builder()
                .legajoId(legajoId)
                .anho(anho)
                .mes(mes)
                .diferencia((byte) 1)
                .build();
        when(legajoContabilidadService.findByUnique(legajoId, anho, mes)).thenReturn(contabilidad);

        // Ejecutar
        ImputacionIndividualResponse response = contableService.getImputacionIndividual(legajoId, anho, mes);

        // Aserciones generales
        assertNotNull(response);
        assertEquals(legajoId, response.getLegajoId());
        assertEquals(anho, response.getAnho());
        assertEquals(mes, response.getMes());
        assertEquals((byte) 1, response.getDiferencia());

        // Aserciones de cargos (categorías)
        assertEquals(1, response.getCargos().size());
        assertEquals("Administrativo Principal", response.getCargos().get(0).getCategoriaNombre());
        assertEquals("FI-CENTRAL", response.getCargos().get(0).getDependenciaAcronimo());
        assertEquals("Facultad de Ingeniería", response.getCargos().get(0).getFacultadNombre());
        assertEquals("Sede Central", response.getCargos().get(0).getGeograficaNombre());
        assertEquals(new BigDecimal("50000.00"), response.getCargos().get(0).getBasico());
        assertEquals(new BigDecimal("10000.00"), response.getCargos().get(0).getAntiguedad());

        // Aserciones de cargos con clase
        assertEquals(1, response.getCargosClase().size());
        assertEquals("Profesor Titular", response.getCargosClase().get(0).getCargoClaseNombre());
        assertEquals(new BigDecimal("30000.00"), response.getCargosClase().get(0).getBasico());
        assertEquals(new BigDecimal("5000.00"), response.getCargosClase().get(0).getAntiguedad());

        // Aserciones de códigos
        assertEquals(2, response.getCodigos().size());
        assertTrue(response.getCodigos().get(0).getRemunerativo());
        assertFalse(response.getCodigos().get(1).getRemunerativo());

        // Aserciones de totales:
        // Total cargos: 50.000 + 10.000 = 60.000
        // Total clases: 30.000 + 5.000 = 35.000
        // Remunerativo códigos: 15.000
        // Total Bruto: 60.000 + 35.000 + 15.000 = 110.000
        // No remunerativo: 7.000
        assertEquals(new BigDecimal("60000.00"), response.getTotales().getTotalCargosBasico().add(response.getTotales().getTotalCargosAntiguedad()));
        assertEquals(new BigDecimal("35000.00"), response.getTotales().getTotalClasesBasico().add(response.getTotales().getTotalClasesAntiguedad()));
        assertEquals(new BigDecimal("22000.00"), response.getTotales().getTotalCodigosImporte());
        assertEquals(new BigDecimal("110000.00"), response.getTotales().getTotalBruto());
        assertEquals(new BigDecimal("7000.00"), response.getTotales().getTotalNoRemunerativo());
    }

    @Test
    void getImputacionIndividual_handlesMissingLegajoContabilidadGracefully() {
        Long legajoId = 9999L;
        Integer anho = 2026;
        Integer mes = 3;

        when(legajoCategoriaImputacionService.findAllByLegajo(legajoId, anho, mes)).thenReturn(List.of());
        when(legajoCargoClaseImputacionService.findAllByLegajo(legajoId, anho, mes)).thenReturn(List.of());
        when(legajoCodigoImputacionService.findAllByLegajo(legajoId, anho, mes)).thenReturn(List.of());
        when(categoriaService.findAll()).thenReturn(List.of());
        when(cargoClaseService.findAll()).thenReturn(List.of());
        when(codigoService.findAll()).thenReturn(List.of());
        when(facultadService.getAllFacultades()).thenReturn(List.of());
        when(geograficaService.getAllGeograficas()).thenReturn(List.of());
        when(dependenciaService.findAll()).thenReturn(List.of());
        when(codigoGrupoService.findAllByRemunerativo((byte) 1)).thenReturn(List.of());
        when(legajoContabilidadService.findByUnique(legajoId, anho, mes)).thenThrow(new LegajoContabilidadException(legajoId, anho, mes));

        ImputacionIndividualResponse response = contableService.getImputacionIndividual(legajoId, anho, mes);

        assertNotNull(response);
        assertEquals((byte) 0, response.getDiferencia());
        assertEquals(BigDecimal.ZERO, response.getTotales().getTotalBruto());
        assertEquals(BigDecimal.ZERO, response.getTotales().getTotalNoRemunerativo());
    }
}
