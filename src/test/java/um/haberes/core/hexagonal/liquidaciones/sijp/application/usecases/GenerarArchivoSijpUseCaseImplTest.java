package um.haberes.core.hexagonal.liquidaciones.sijp.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import um.haberes.core.hexagonal.liquidaciones.codigo.application.service.CodigoService;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.item.application.service.ItemService;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.service.NovedadService;
import um.haberes.core.hexagonal.liquidaciones.novedad.domain.model.Novedad;
import um.haberes.core.hexagonal.liquidaciones.sijp.application.exception.SijpException;
import um.haberes.core.hexagonal.liquidaciones.sijp.application.exception.SijpSinControlException;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.ArchivoSijp;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.VarianteSijp;
import um.haberes.core.hexagonal.liquidaciones.sijp.domain.model.layout.SijpLayout;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;
import um.haberes.core.model.CodigoGrupoEntity;
import um.haberes.core.model.ControlEntity;
import um.haberes.core.model.LegajoControlEntity;
import um.haberes.core.service.CodigoGrupoService;
import um.haberes.core.service.ControlService;
import um.haberes.core.service.LegajoControlService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class GenerarArchivoSijpUseCaseImplTest {

    private static final Integer ANHO = 2026;
    private static final Integer MES = 11;
    private static final Long LEGAJO = 1001L;
    private static final String CUIL = "20123456789";

    @Mock
    private LegajoControlService legajoControlService;

    @Mock
    private ControlService controlService;

    @Mock
    private ItemService itemService;

    @Mock
    private NovedadService novedadService;

    @Mock
    private CodigoService codigoService;

    @Mock
    private CodigoGrupoService codigoGrupoService;

    @InjectMocks
    private GenerarArchivoSijpUseCaseImpl useCase;

    private ControlEntity control;
    private PersonaEntity persona;
    private LegajoControlEntity legajo;

    @BeforeEach
    void setUp() {
        control = control(ANHO, MES);
        persona = persona();
        legajo = legajo(persona);

        when(controlService.findByPeriodoOpcional(ANHO, MES)).thenReturn(Optional.of(control));
        when(legajoControlService.findAllLiquidadoByPeriodo(ANHO, MES)).thenReturn(List.of(legajo));
        when(itemService.getItemsByPeriodo(ANHO, MES)).thenReturn(itemsNormales());
        when(novedadService.findAllByCodigo(18, ANHO, MES)).thenReturn(List.of());
        when(codigoService.findAll()).thenReturn(codigos((byte) 1));
        when(codigoGrupoService.findAllByRemunerativo((byte) 1)).thenReturn(gruposRemunerativos());
    }

    @Test
    void generaLaVarianteNormalConLasReglasDelLegacy() {
        ArchivoSijp archivo = useCase.generar(ANHO, MES, VarianteSijp.NORMAL);

        assertThat(archivo.getVariante()).isEqualTo(VarianteSijp.NORMAL);
        assertThat(archivo.getNombreArchivo()).isEqualTo("sijp-2026-11.txt");
        assertThat(archivo.getCantidad()).isEqualTo(1);
        assertThat(new String(archivo.getContenido(), SijpLayout.CHARSET_ANSI)).endsWith("\r\n");

        String linea = linea(archivo);
        assertThat(linea).hasSize(SijpLayout.LONGITUD_LINEA);
        assertThat(campo(linea, 1, 11)).isEqualTo(CUIL);
        assertThat(campo(linea, 12, 30)).isEqualTo("PEREZ, JUAN" + " ".repeat(19));
        assertThat(campo(linea, 70, 12)).isEqualTo("   110000,00");
        assertThat(campo(linea, 82, 12)).isEqualTo("   100000,00");
        assertThat(campo(linea, 189, 12)).isEqualTo("   100000,00");
        assertThat(campo(linea, 201, 12)).isEqualTo("   100000,00");
        assertThat(campo(linea, 213, 12)).isEqualTo("   100000,00");
        assertThat(campo(linea, 260, 12)).isEqualTo("    92000,00");
        assertThat(campo(linea, 272, 12)).isEqualTo("     8000,00");
        assertThat(campo(linea, 320, 9)).isEqualTo("    30,00");
        assertThat(campo(linea, 406, 12)).isEqualTo("     8000,00");
        assertThat(campo(linea, 464, 12)).isEqualTo("     7003,68");
        assertThat(campo(linea, 476, 12)).isEqualTo("     2000,00");
    }

    @Test
    void corrigeLosDiasTrabajadosConLaNovedad18() {
        when(novedadService.findAllByCodigo(18, ANHO, MES)).thenReturn(List.of(novedad(new BigDecimal("2"))));

        ArchivoSijp archivo = useCase.generar(ANHO, MES, VarianteSijp.NORMAL);

        assertThat(campo(linea(archivo), 320, 9)).isEqualTo("    28,00");
    }

    @Test
    void laVarianteEtecDescuentaElAporteSolidarioDelBruto() {
        when(codigoService.findAll()).thenReturn(codigos((byte) 0));

        ArchivoSijp archivo = useCase.generar(ANHO, MES, VarianteSijp.ETEC);

        assertThat(archivo.getNombreArchivo()).isEqualTo("sijp-2026-11-etec.txt");

        String linea = linea(archivo);
        assertThat(campo(linea, 70, 12)).isEqualTo("    98000,00");
        assertThat(campo(linea, 189, 12)).isEqualTo("    98000,00");
        assertThat(campo(linea, 201, 12)).isEqualTo("    98000,00");
        assertThat(campo(linea, 213, 12)).isEqualTo("    98000,00");
        assertThat(campo(linea, 260, 12)).isEqualTo("    90000,00");
        assertThat(campo(linea, 406, 12)).isEqualTo("        0,00");
        assertThat(campo(linea, 464, 12)).isEqualTo("        0,00");
        assertThat(campo(linea, 476, 12)).isEqualTo("        0,00");
    }

    @Test
    void aplicaLosAjustesEtecAlLegajoOnlyEtec() {
        when(codigoService.findAll()).thenReturn(codigos((byte) 1));

        ArchivoSijp archivo = useCase.generar(ANHO, MES, VarianteSijp.ETEC);

        String linea = linea(archivo);
        assertThat(campo(linea, 49, 3)).isEqualTo("038");
        assertThat(campo(linea, 237, 1)).isEqualTo("7");
        assertThat(campo(linea, 82, 12)).isEqualTo("        0,00");
        assertThat(campo(linea, 201, 12)).isEqualTo("        0,00");
        assertThat(campo(linea, 342, 12)).isEqualTo(campo(linea, 189, 12));
        assertThat(campo(linea, 391, 12)).isEqualTo(campo(linea, 189, 12));
        assertThat(campo(linea, 464, 12)).isEqualTo("        0,00");
    }

    @Test
    void enEtecSinOnlyEtecMantieneElTipoDeEmpleadoNormal() {
        when(codigoService.findAll()).thenReturn(codigos((byte) 0));

        ArchivoSijp archivo = useCase.generar(ANHO, MES, VarianteSijp.ETEC);

        String linea = linea(archivo);
        assertThat(campo(linea, 49, 3)).isEqualTo("001");
        assertThat(campo(linea, 237, 1)).isEqualTo("1");
        assertThat(campo(linea, 342, 12)).isEqualTo("        0,00");
    }

    @Test
    void aplicaElCoeficienteDeUnComaCincoEnDiciembre() {
        ControlEntity diciembre = control(2026, 12);
        diciembre.setMaximo1sijp(new BigDecimal("1000000"));
        diciembre.setMaximo4sijp(new BigDecimal("100000"));
        diciembre.setMaximo5sijp(new BigDecimal("1000000"));
        when(controlService.findByPeriodoOpcional(2026, 12)).thenReturn(Optional.of(diciembre));
        when(legajoControlService.findAllLiquidadoByPeriodo(2026, 12)).thenReturn(List.of(legajo));
        when(itemService.getItemsByPeriodo(2026, 12)).thenReturn(List.of(
                item(96, "200000"), item(97, "0"), item(3, "0"), item(51, "0")));
        when(novedadService.findAllByCodigo(18, 2026, 12)).thenReturn(List.of());
        when(codigoService.findAll()).thenReturn(codigos((byte) 1));
        when(codigoGrupoService.findAllByRemunerativo((byte) 1)).thenReturn(List.of());

        ArchivoSijp archivo = useCase.generar(2026, 12, VarianteSijp.NORMAL);

        assertThat(campo(linea(archivo), 213, 12)).isEqualTo("   150000,00");
    }

    @Test
    void fallaSiElPeriodoNoTieneControl() {
        when(controlService.findByPeriodoOpcional(ANHO, MES)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.generar(ANHO, MES, VarianteSijp.NORMAL))
                .isInstanceOf(SijpSinControlException.class)
                .hasMessageContaining("2026/11");
    }

    @Test
    void fallaSiNoHayLegajosLiquidados() {
        when(legajoControlService.findAllLiquidadoByPeriodo(ANHO, MES)).thenReturn(List.of());

        assertThatThrownBy(() -> useCase.generar(ANHO, MES, VarianteSijp.NORMAL))
                .isInstanceOf(SijpException.class)
                .hasMessageContaining("No hay legajos liquidados");
    }

    @Test
    void fallaSiUnLegajoNoTieneCuilValido() {
        persona.setCuil("123");

        assertThatThrownBy(() -> useCase.generar(ANHO, MES, VarianteSijp.NORMAL))
                .isInstanceOf(SijpException.class)
                .hasMessageContaining("CUIL")
                .hasMessageContaining(String.valueOf(LEGAJO));
    }

    @Test
    void fallaSiUnLegajoNoTienePersona() {
        legajo.setPersona(null);

        assertThatThrownBy(() -> useCase.generar(ANHO, MES, VarianteSijp.NORMAL))
                .isInstanceOf(SijpException.class)
                .hasMessageContaining("sin persona")
                .hasMessageContaining(String.valueOf(LEGAJO));
    }

    @Test
    void normalizaElCuilConSeparadores() {
        persona.setCuil("20-12345678-9");

        ArchivoSijp archivo = useCase.generar(ANHO, MES, VarianteSijp.NORMAL);

        assertThat(campo(linea(archivo), 1, 11)).isEqualTo("20123456789");
    }

    @Test
    void rechazaUnPeriodoInvalido() {
        assertThatThrownBy(() -> useCase.generar(1999, 11, VarianteSijp.NORMAL))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Año inválido");
        assertThatThrownBy(() -> useCase.generar(2026, 13, VarianteSijp.NORMAL))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Mes inválido");
    }

    private String linea(ArchivoSijp archivo) {
        return new String(archivo.getContenido(), SijpLayout.CHARSET_ANSI).split("\r\n")[0];
    }

    private String campo(String linea, int posicion, int largo) {
        return linea.substring(posicion - 1, posicion - 1 + largo);
    }

    private ControlEntity control(Integer anho, Integer mes) {
        ControlEntity control = new ControlEntity();
        control.setAnho(anho);
        control.setMes(mes);
        control.setFechaHasta(OffsetDateTime.of(anho, mes, 28, 0, 0, 0, 0, ZoneOffset.UTC));
        control.setMaximo1sijp(new BigDecimal("1000000"));
        control.setMaximo2sijp(new BigDecimal("1000000"));
        control.setMaximo3sijp(new BigDecimal("1000000"));
        control.setMaximo4sijp(new BigDecimal("1000000"));
        control.setMaximo5sijp(new BigDecimal("1000000"));
        control.setMinimoAporte(BigDecimal.ZERO);
        return control;
    }

    private PersonaEntity persona() {
        return PersonaEntity.builder()
                .legajoId(LEGAJO)
                .apellido("PEREZ")
                .nombre("JUAN")
                .cuil(CUIL)
                .estado(0)
                .estadoAfip(1)
                .situacionAfip(1)
                .actividadAfip(1)
                .localidadAfip(1)
                .modeloContratacionAfip(1)
                .obraSocial(0L)
                .build();
    }

    private LegajoControlEntity legajo(PersonaEntity persona) {
        LegajoControlEntity legajo = new LegajoControlEntity();
        legajo.setLegajoId(LEGAJO);
        legajo.setAnho(ANHO);
        legajo.setMes(MES);
        legajo.setLiquidado((byte) 1);
        legajo.setPersona(persona);
        return legajo;
    }

    private List<Item> itemsNormales() {
        return List.of(item(96, "100000"), item(97, "10000"), item(3, "8000"), item(51, "2000"));
    }

    private Item item(int codigoId, String importe) {
        return Item.builder()
                .legajoId(LEGAJO)
                .anho(ANHO)
                .mes(MES)
                .codigoId(codigoId)
                .importe(new BigDecimal(importe))
                .build();
    }

    private Novedad novedad(BigDecimal importe) {
        return Novedad.builder()
                .legajoId(LEGAJO)
                .anho(ANHO)
                .mes(MES)
                .codigoId(18)
                .importe(importe)
                .build();
    }

    private List<Codigo> codigos(byte incluidoEtec) {
        return List.of(
                Codigo.builder().codigoId(96).incluidoEtec(incluidoEtec).build(),
                Codigo.builder().codigoId(97).incluidoEtec(incluidoEtec).build(),
                Codigo.builder().codigoId(3).incluidoEtec(incluidoEtec).build(),
                Codigo.builder().codigoId(51).incluidoEtec(incluidoEtec).build());
    }

    private List<CodigoGrupoEntity> gruposRemunerativos() {
        return List.of(grupo(96), grupo(97), grupo(3), grupo(51));
    }

    private CodigoGrupoEntity grupo(Integer codigoId) {
        CodigoGrupoEntity grupo = new CodigoGrupoEntity();
        grupo.setCodigoId(codigoId);
        grupo.setRemunerativo((byte) 1);
        return grupo;
    }

}
