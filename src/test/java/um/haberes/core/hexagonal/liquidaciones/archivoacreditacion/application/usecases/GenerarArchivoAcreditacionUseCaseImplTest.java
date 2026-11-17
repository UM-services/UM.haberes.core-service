package um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.service.AcreditacionService;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.application.exception.ArchivoAcreditacionException;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.ArchivoAcreditacion;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.ArchivoAcreditacionFiltro;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.model.layout.SantanderLayout;
import um.haberes.core.hexagonal.liquidaciones.archivoacreditacion.domain.ports.in.PrepararAcreditacionesUseCase;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.service.LiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.domain.model.Liquidacion;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.entity.LiquidacionEntity;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.infrastructure.persistence.mapper.LiquidacionMapper;
import um.haberes.core.hexagonal.personas.persona.infrastructure.persistence.entity.PersonaEntity;
import um.haberes.core.model.ExcluidoEntity;
import um.haberes.core.model.LegajoBancoEntity;
import um.haberes.core.service.ExcluidoService;
import um.haberes.core.service.LegajoBancoService;
import um.haberes.core.service.facade.OrdenPagoService;

@ExtendWith(MockitoExtension.class)
class GenerarArchivoAcreditacionUseCaseImplTest {

    private static final String CBU = "072" + "0".repeat(19);
    private static final LocalDate FECHA_PAGO = LocalDate.of(2026, 11, 30);
    private static final OffsetDateTime FECHA_UTC = FECHA_PAGO.atStartOfDay().atOffset(ZoneOffset.UTC);

    @Mock
    private LegajoBancoService legajoBancoService;

    @Mock
    private ExcluidoService excluidoService;

    @Mock
    private LiquidacionService liquidacionService;

    @Mock
    private LiquidacionMapper liquidacionMapper;

    @Mock
    private AcreditacionService acreditacionService;

    @Mock
    private OrdenPagoService ordenPagoService;

    @Mock
    private PrepararAcreditacionesUseCase prepararAcreditacionesUseCase;

    @InjectMocks
    private GenerarArchivoAcreditacionUseCaseImpl useCase;

    private ArchivoAcreditacionFiltro filtro;

    @BeforeEach
    void setUp() {
        filtro = ArchivoAcreditacionFiltro.builder()
                .anho(2026)
                .mes(11)
                .fechaPago(FECHA_PAGO)
                .numEnvio(1)
                .salida("T")
                .dependenciaId(0)
                .codigoId(0)
                .cci(false)
                .individual(false)
                .build();
    }

    @Test
    void generaElArchivoConLosAceptadosYDevuelveLosRechazos() {
        LegajoBancoEntity ok = legajoBanco(1L, CBU, new BigDecimal("1000.00"), "20123456789", 11L);
        LegajoBancoEntity excluido = legajoBanco(2L, CBU, new BigDecimal("500.00"), "27222222222", 22L);
        LegajoBancoEntity cbuInvalido = legajoBanco(3L, "072123", new BigDecimal("400.00"), "23333333333", 33L);
        when(legajoBancoService.findAllSantander("T", 2026, 11, 0)).thenReturn(List.of(ok, excluido, cbuInvalido));
        when(excluidoService.findAllByPeriodo(2026, 11))
                .thenReturn(List.of(new ExcluidoEntity(1L, 2L, 2026, 11, null, "")));
        when(liquidacionMapper.toDomain(ok.getLiquidacion()))
                .thenReturn(Liquidacion.builder().liquidacionId(11L).build());
        when(acreditacionService.findByPeriodo(2026, 11))
                .thenReturn(Acreditacion.builder().acreditacionId(5L).anho(2026).mes(11).acreditado((byte) 0).build());

        ArchivoAcreditacion archivo = useCase.generarSantander(filtro);

        assertThat(archivo.getCantidad()).isEqualTo(1);
        assertThat(archivo.getTotal()).isEqualByComparingTo("1000.00");
        assertThat(archivo.isPeriodoAcreditado()).isTrue();
        assertThat(archivo.getNombreArchivo()).isEqualTo("santander-2026-11.txt");
        assertThat(archivo.getRechazos()).hasSize(2);
        assertThat(archivo.getRechazos()).extracting("motivo")
                .containsExactly("Excluido", "Error de CBU");
        assertThat(archivo.getRechazos()).extracting("legajoId").containsExactly(2L, 3L);

        String contenido = new String(archivo.getContenido(), SantanderLayout.CHARSET_ANSI);
        String[] lineas = contenido.split("\r\n");
        assertThat(lineas).hasSize(3);
        assertThat(lineas[0]).startsWith("H").hasSize(SantanderLayout.LONGITUD_LINEA);
        assertThat(lineas[0].substring(16, 18)).isEqualTo("01");
        assertThat(lineas[1]).startsWith("D").hasSize(SantanderLayout.LONGITUD_LINEA);
        assertThat(lineas[2]).startsWith("T").hasSize(SantanderLayout.LONGITUD_LINEA);
        assertThat(lineas[2].substring(16, 31)).isEqualTo("000000000100000");
        assertThat(lineas[2].substring(31, 38)).isEqualTo("0000001");

        verify(liquidacionService).acreditado(any(Liquidacion.class), eq(FECHA_UTC));
        verify(acreditacionService).update(any(Acreditacion.class), eq(5L));
        verify(ordenPagoService).generateOrdenPago(any());
    }

    @Test
    void usaAcuerdoYFormaDePagoDeCciEnOtrosBancos() {
        LegajoBancoEntity ok = legajoBanco(1L, "011" + "0".repeat(19), new BigDecimal("250.00"),
                "20123456789", 11L);
        when(legajoBancoService.findAllOtrosBancos("T", 2026, 11, 0)).thenReturn(List.of(ok));
        when(excluidoService.findAllByPeriodo(2026, 11)).thenReturn(List.of());
        when(liquidacionMapper.toDomain(ok.getLiquidacion()))
                .thenReturn(Liquidacion.builder().liquidacionId(11L).build());
        when(acreditacionService.findByPeriodo(2026, 11))
                .thenReturn(Acreditacion.builder().acreditacionId(5L).anho(2026).mes(11).acreditado((byte) 0).build());
        filtro.setCci(true);

        ArchivoAcreditacion archivo = useCase.generarOtrosBancos(filtro);

        String contenido = new String(archivo.getContenido(), SantanderLayout.CHARSET_ANSI);
        assertThat(contenido).contains("H30518594466" + "0" + "011" + "03");
        assertThat(archivo.getNombreArchivo()).isEqualTo("otrosbancos-2026-11-cci.txt");
    }

    @Test
    void fallaSiNoHayNingunLegajoParaAcreditar() {
        LegajoBancoEntity cbuInvalido = legajoBanco(3L, "072123", new BigDecimal("400.00"), "23333333333", 33L);
        when(legajoBancoService.findAllSantander("T", 2026, 11, 0)).thenReturn(List.of(cbuInvalido));
        when(excluidoService.findAllByPeriodo(2026, 11)).thenReturn(List.of());

        assertThatThrownBy(() -> useCase.generarSantander(filtro))
                .isInstanceOf(ArchivoAcreditacionException.class)
                .hasMessageContaining("Ningún legajo");

        verify(acreditacionService, never()).update(any(Acreditacion.class), any(Long.class));
        verify(ordenPagoService, never()).generateOrdenPago(any());
    }

    @Test
    void rechazaCuilInvalidoSinPerderElArchivo() {
        LegajoBancoEntity sinCuil = legajoBanco(4L, CBU, new BigDecimal("100.00"), "123", 44L);
        LegajoBancoEntity ok = legajoBanco(1L, CBU, new BigDecimal("100.00"), "20123456789", 11L);
        when(legajoBancoService.findAllSantander("T", 2026, 11, 0)).thenReturn(List.of(sinCuil, ok));
        when(excluidoService.findAllByPeriodo(2026, 11)).thenReturn(List.of());
        when(liquidacionMapper.toDomain(ok.getLiquidacion()))
                .thenReturn(Liquidacion.builder().liquidacionId(11L).build());
        when(acreditacionService.findByPeriodo(2026, 11))
                .thenReturn(Acreditacion.builder().acreditacionId(5L).anho(2026).mes(11).acreditado((byte) 0).build());

        ArchivoAcreditacion archivo = useCase.generarSantander(filtro);

        assertThat(archivo.getCantidad()).isEqualTo(1);
        assertThat(archivo.getRechazos()).extracting("motivo").containsExactly("Error de CUIL");
    }

    private static LegajoBancoEntity legajoBanco(Long legajoId, String cbu, BigDecimal acreditado, String cuil,
            Long liquidacionId) {
        PersonaEntity persona = PersonaEntity.builder()
                .legajoId(legajoId)
                .apellido("PEREZ")
                .nombre("JUAN")
                .cuil(cuil)
                .build();
        LiquidacionEntity liquidacion = new LiquidacionEntity();
        liquidacion.setLiquidacionId(liquidacionId);
        liquidacion.setLegajoId(legajoId);
        liquidacion.setAnho(2026);
        liquidacion.setMes(11);
        return new LegajoBancoEntity(null, legajoId, 2026, 11, cbu, BigDecimal.ZERO, BigDecimal.ZERO, (byte) 0,
                acreditado, persona, liquidacion);
    }
}
