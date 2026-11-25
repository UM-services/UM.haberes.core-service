package um.haberes.core.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.item.application.service.ItemService;
import um.haberes.core.hexagonal.liquidaciones.liquidacion.application.service.LiquidacionService;
import um.haberes.core.hexagonal.liquidaciones.novedad.application.service.NovedadService;
import um.haberes.core.model.LegajoBancoEntity;
import um.haberes.core.repository.JpaLegajoBancoRepository;

@ExtendWith(MockitoExtension.class)
class LegajoBancoServiceTest {

    @Mock
    private JpaLegajoBancoRepository repository;

    @Mock
    private ItemService itemService;

    @Mock
    private LiquidacionService liquidacionService;

    @Mock
    private NovedadService novedadService;

    @InjectMocks
    private LegajoBancoService service;

    @Test
    void updateUsaElIdDeLaRutaAunqueElCuerpoNoLoTraine() {
        LegajoBancoEntity existente = new LegajoBancoEntity();
        existente.setLegajoBancoId(3L);
        when(repository.findByLegajoBancoId(3L)).thenReturn(Optional.of(existente));
        when(repository.save(any(LegajoBancoEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LegajoBancoEntity cambios = new LegajoBancoEntity();
        cambios.setLegajoId(123L);
        cambios.setAnho(2026);
        cambios.setMes(11);
        cambios.setCbu("0720000000000000000001");
        cambios.setFijo(BigDecimal.ZERO);
        cambios.setPorcentaje(BigDecimal.ZERO);
        cambios.setResto((byte) 1);
        cambios.setAcreditado(new BigDecimal("1234.56"));

        LegajoBancoEntity actualizado = service.update(cambios, 3L);

        assertThat(actualizado.getLegajoBancoId()).isEqualTo(3L);
        assertThat(actualizado.getAcreditado()).isEqualByComparingTo("1234.56");
    }

    @Test
    void updateConservaElAcreditadoExistenteSiElCuerpoNoLoTrae() {
        LegajoBancoEntity existente = new LegajoBancoEntity();
        existente.setLegajoBancoId(3L);
        existente.setAcreditado(new BigDecimal("1000.00"));
        when(repository.findByLegajoBancoId(3L)).thenReturn(Optional.of(existente));
        when(repository.save(any(LegajoBancoEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LegajoBancoEntity cambios = new LegajoBancoEntity();
        cambios.setLegajoId(123L);
        cambios.setAnho(2026);
        cambios.setMes(11);
        cambios.setCbu("0720000000000000000001");
        cambios.setResto((byte) 1);

        LegajoBancoEntity actualizado = service.update(cambios, 3L);

        assertThat(actualizado.getAcreditado()).isEqualByComparingTo("1000.00");
    }

    @Test
    void addCompletaElAcreditadoEnCeroCuandoNoSeInforma() {
        when(repository.save(any(LegajoBancoEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        LegajoBancoEntity nuevo = new LegajoBancoEntity();
        nuevo.setLegajoId(123L);
        nuevo.setAnho(2026);
        nuevo.setMes(11);
        nuevo.setCbu("0720000000000000000001");

        service.add(nuevo);

        assertThat(nuevo.getAcreditado()).isEqualByComparingTo("0.00");
    }
}
