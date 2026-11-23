package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.audit.AuditBulkScope;
import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class DeleteCargosNotInByPeriodoUseCaseImplTest {

    @Mock
    private CargoLiquidacionRepository cargoLiquidacionRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private AuditBulkService auditBulk;

    @Mock
    private AuditBulkScope auditScope;

    @InjectMocks
    private DeleteCargosNotInByPeriodoUseCaseImpl useCase;

    @Test
    void protegeLasCategoriasEtecDelPeriodo() {
        when(auditBulk.begin(any())).thenReturn(auditScope);
        when(categoriaRepository.findAllByPeriodo(2026, 11)).thenReturn(List.of(
                Categoria.builder().categoriaId(992).puntos(new BigDecimal("1738.00")).build(),
                Categoria.builder().categoriaId(1).puntos(BigDecimal.ZERO).build()));

        useCase.deleteCargosNotInByPeriodo(List.of(100L), 2026, 11);

        verify(cargoLiquidacionRepository).deleteAllByLegajoIdNotInAndAnhoAndMesAndCategoriaIdNotIn(
                List.of(100L), 2026, 11, List.of(992));
    }
}
