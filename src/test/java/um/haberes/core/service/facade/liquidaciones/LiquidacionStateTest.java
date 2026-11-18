package um.haberes.core.service.facade.liquidaciones;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.model.ControlEntity;

class LiquidacionStateTest {

    @Test
    void copiaElControlEtecDelPeriodo() {
        ControlEtec controlEtec = ControlEtec.builder()
                .anho(2026)
                .mes(11)
                .valorPuntoEtec(new BigDecimal("311.3633"))
                .montoFijoCargo(new BigDecimal("247543.34"))
                .porcentajePresentismo(new BigDecimal("20.00"))
                .porcentajeAdicionalCarrera(new BigDecimal("10.00"))
                .garantia(new BigDecimal("844872.78"))
                .horasAdicionalCargaHoraria(new BigDecimal("15.00"))
                .valorHoraAdicionalCargaHoraria(new BigDecimal("8012.19"))
                .build();
        LiquidacionState origen = LiquidacionState.builder()
                .control(new ControlEntity())
                .persona(new Persona())
                .indices(List.of(BigDecimal.ONE))
                .codigos(Map.of(985, new Codigo()))
                .novedades(Map.of())
                .controlEtec(controlEtec)
                .build();

        LiquidacionState destino = new LiquidacionState();
        destino.copiarContexto(origen);

        assertThat(destino.getControlEtec()).isSameAs(controlEtec);
        assertThat(destino.getControlEtec().getMontoFijoCargo()).isEqualByComparingTo("247543.34");
        assertThat(destino.getControl()).isSameAs(origen.getControl());
        assertThat(destino.getPersona()).isSameAs(origen.getPersona());
        assertThat(destino.getIndices()).isSameAs(origen.getIndices());
        assertThat(destino.getCodigos()).isSameAs(origen.getCodigos());
        assertThat(destino.getNovedades()).isSameAs(origen.getNovedades());
    }

    @Test
    void elEstadoDestinoMantieneSuPropioAcumuladorEtec() {
        LiquidacionState origen = LiquidacionState.builder()
                .controlEtec(ControlEtec.builder().anho(2026).mes(11).build())
                .build();

        LiquidacionState destino = new LiquidacionState();
        destino.copiarContexto(origen);

        assertThat(destino.getEtecAcumulado()).isNotNull();
        assertThat(destino.getEtecAcumulado().hayCargos()).isFalse();
    }
}
