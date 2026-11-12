package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.exception.CargoLiquidacionException;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.model.CargoLiquidacion;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.in.SaveAllCargoLiquidacionesUseCase;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;

@ExtendWith(MockitoExtension.class)
class ReplaceCargosByCategoriaUseCaseImplTest {

    @Mock
    private CargoLiquidacionRepository repository;

    @Mock
    private SaveAllCargoLiquidacionesUseCase saveAll;

    @Mock
    private AuditBulkService auditBulk;

    @InjectMocks
    private ReplaceCargosByCategoriaUseCaseImpl useCase;

    private CargoLiquidacion cargo(Integer categoriaId) {
        return CargoLiquidacion.builder()
                .legajoId(1234L)
                .anho(2026)
                .mes(11)
                .categoriaId(categoriaId)
                .categoriaBasico(new BigDecimal("3000000.00"))
                .jornada(1)
                .situacion("A")
                .build();
    }

    @Test
    void replaceByPeriodoBorraLasCategoriasYGuardaElLote() {
        List<CargoLiquidacion> cargos = List.of(cargo(9001), cargo(9002));
        when(saveAll.saveAllCargoLiquidaciones(cargos, null)).thenReturn(cargos);

        List<CargoLiquidacion> result = useCase.replaceByPeriodo(2026, 11, List.of(9001, 9002), cargos);

        assertEquals(cargos, result);
        verify(repository).deleteAllByAnhoAndMesAndCategoriaIdIn(2026, 11, List.of(9001, 9002));
        verify(saveAll).saveAllCargoLiquidaciones(cargos, null);
    }

    @Test
    void replaceByLegajoBorraSoloElLegajo() {
        List<CargoLiquidacion> cargos = List.of(cargo(9001));
        when(saveAll.saveAllCargoLiquidaciones(cargos, null)).thenReturn(cargos);

        useCase.replaceByLegajo(1234L, 2026, 11, List.of(9001), cargos);

        verify(repository).deleteAllByLegajoIdAndAnhoAndMesAndCategoriaIdIn(1234L, 2026, 11, List.of(9001));
        verify(saveAll).saveAllCargoLiquidaciones(cargos, null);
    }

    @Test
    void rechazaCargoFueraDelAlcanceDeCategorias() {
        assertThrows(CargoLiquidacionException.class,
                () -> useCase.replaceByPeriodo(2026, 11, List.of(9001), List.of(cargo(9002))));
        verifyNoInteractions(repository, saveAll, auditBulk);
    }

    @Test
    void rechazaCargoDeOtroPeriodo() {
        CargoLiquidacion otroPeriodo = CargoLiquidacion.builder()
                .legajoId(1234L).anho(2026).mes(10).categoriaId(9001).build();

        assertThrows(CargoLiquidacionException.class,
                () -> useCase.replaceByPeriodo(2026, 11, List.of(9001), List.of(otroPeriodo)));
        verifyNoInteractions(repository, saveAll, auditBulk);
    }

    @Test
    void rechazaAlcanceVacio() {
        assertThrows(CargoLiquidacionException.class,
                () -> useCase.replaceByPeriodo(2026, 11, List.of(), List.of()));
        verifyNoInteractions(repository, saveAll, auditBulk);
    }

    @Test
    void noBorraNadaSiLaValidacionFalla() {
        assertThrows(CargoLiquidacionException.class,
                () -> useCase.replaceByLegajo(1234L, 2026, 11, List.of(9001),
                        List.of(CargoLiquidacion.builder().legajoId(9999L).anho(2026).mes(11)
                                .categoriaId(9001).build())));
        verifyNoInteractions(repository, saveAll, auditBulk);
    }
}
