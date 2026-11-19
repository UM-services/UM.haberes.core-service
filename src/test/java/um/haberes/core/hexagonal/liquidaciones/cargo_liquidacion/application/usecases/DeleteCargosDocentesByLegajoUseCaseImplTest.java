package um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.application.usecases;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.audit.AuditBulkService;
import um.haberes.core.hexagonal.liquidaciones.cargo_liquidacion.domain.ports.out.CargoLiquidacionRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class DeleteCargosDocentesByLegajoUseCaseImplTest {

    @Mock
    private CargoLiquidacionRepository cargoLiquidacionRepository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @Mock
    private AuditBulkService auditBulk;

    @InjectMocks
    private DeleteCargosDocentesByLegajoUseCaseImpl useCase;

    private Categoria categoria(Integer id, String puntos) {
        return Categoria.builder()
                .categoriaId(id)
                .nombre("CAT " + id)
                .docente((byte) 1)
                .puntos(new BigDecimal(puntos))
                .build();
    }

    @Test
    void excluyeDelBorradoLasCategoriasConPuntos() {
        when(categoriaRepository.findAllByDocente((byte) 1))
                .thenReturn(List.of(categoria(1, "0.00"), categoria(1307, "1000.00")));

        useCase.deleteCargosDocentesByLegajo(1234L, 2026, 11);

        @SuppressWarnings("unchecked")
        ArgumentCaptor<List<Integer>> captor = ArgumentCaptor.forClass(List.class);
        verify(cargoLiquidacionRepository).deleteAllByLegajoIdAndAnhoAndMesAndSituacionAndCategoriaIdIn(
                eq(1234L), eq(2026), eq(11), eq("A"), captor.capture());
        assertTrue(captor.getValue().contains(1));
        assertFalse(captor.getValue().contains(1307));
    }
}
