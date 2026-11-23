package um.haberes.core.hexagonal.etec.hora_escala.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.etec.control_etec.domain.model.ControlEtec;
import um.haberes.core.hexagonal.etec.control_etec.domain.ports.out.ControlEtecRepository;
import um.haberes.core.hexagonal.etec.hora_escala.application.exception.EscalaHoraEtecException;
import um.haberes.core.hexagonal.etec.hora_escala.application.exception.EscalaHoraEtecPeriodoException;
import um.haberes.core.hexagonal.etec.hora_escala.domain.model.EscalaHoraEtec;
import um.haberes.core.hexagonal.etec.hora_escala.domain.ports.out.EscalaHoraEtecRepository;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.model.Categoria;
import um.haberes.core.hexagonal.liquidaciones.categoria.domain.ports.out.CategoriaRepository;

@ExtendWith(MockitoExtension.class)
class ReplaceEscalaHoraEtecUseCaseImplTest {

    private static final Long CONTROL_ID = 50L;

    @Mock
    private ControlEtecRepository controlEtecRepository;

    @Mock
    private EscalaHoraEtecRepository repository;

    @Mock
    private CategoriaRepository categoriaRepository;

    @InjectMocks
    private ReplaceEscalaHoraEtecUseCaseImpl useCase;

    private void conPeriodo() {
        when(controlEtecRepository.findByPeriodo(2026, 11)).thenReturn(Optional.of(
                ControlEtec.builder().controlEtecId(CONTROL_ID).anho(2026).mes(11).build()));
    }

    private void conCategoriaHoras() {
        when(categoriaRepository.findByCategoriaId(2002)).thenReturn(Optional.of(
                Categoria.builder()
                        .categoriaId(2002)
                        .nombre("DOCENTE ETEC HORAS")
                        .docente((byte) 1)
                        .etecHoras((byte) 1)
                        .build()));
    }

    private EscalaHoraEtec tramo(String desde, String hasta, String montoFijo, String valorHora) {
        return EscalaHoraEtec.builder()
                .categoriaId(2002)
                .horasDesde(desde == null ? null : new BigDecimal(desde))
                .horasHasta(hasta == null ? null : new BigDecimal(hasta))
                .montoFijo(montoFijo == null ? null : new BigDecimal(montoFijo))
                .valorHora(valorHora == null ? null : new BigDecimal(valorHora))
                .build();
    }

    @Test
    void reemplazaLaEscalaOrdenadaConDosDecimales() {
        conPeriodo();
        conCategoriaHoras();
        when(repository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        List<EscalaHoraEtec> resultado = useCase.reemplazar(2026, 11, List.of(
                tramo("13", "30", "90000", "6500.5"),
                tramo("0", "12", "50000", "5000")));

        verify(repository).deleteAllByControlEtecId(CONTROL_ID);
        assertThat(resultado).extracting(EscalaHoraEtec::getHorasDesde)
                .containsExactly(new BigDecimal("0.00"), new BigDecimal("13.00"));
        assertThat(resultado.getFirst().getMontoFijo()).isEqualByComparingTo("50000.00");
        assertThat(resultado.getFirst().getValorHora()).isEqualByComparingTo("5000.00");
        assertThat(resultado.get(1).getValorHora()).isEqualByComparingTo("6500.50");
        assertThat(resultado).allSatisfy(tramo -> assertThat(tramo.getControlEtecId())
                .isEqualTo(CONTROL_ID));
        assertThat(resultado).allSatisfy(tramo -> assertThat(tramo.getCategoriaId())
                .isEqualTo(2002));
    }

    @Test
    void laListaVaciaBorraLaEscalaDelPeriodo() {
        conPeriodo();

        List<EscalaHoraEtec> resultado = useCase.reemplazar(2026, 11, List.of());

        verify(repository).deleteAllByControlEtecId(CONTROL_ID);
        verify(repository, never()).saveAll(anyList());
        assertThat(resultado).isEmpty();
    }

    @Test
    void completaConCeroLosImportesNulos() {
        conPeriodo();
        conCategoriaHoras();
        when(repository.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));

        List<EscalaHoraEtec> resultado = useCase.reemplazar(2026, 11,
                List.of(tramo("0", "12", null, null)));

        assertThat(resultado.getFirst().getMontoFijo()).isEqualByComparingTo("0.00");
        assertThat(resultado.getFirst().getValorHora()).isEqualByComparingTo("0.00");
    }

    @Test
    void rechazaTramosSuperpuestosSinBorrarNada() {
        conPeriodo();
        conCategoriaHoras();

        assertThatThrownBy(() -> useCase.reemplazar(2026, 11, List.of(
                tramo("0", "12", "0", "0"), tramo("10", "20", "0", "0"))))
                .isInstanceOf(EscalaHoraEtecException.class)
                .hasMessageContaining("se superponen");

        verify(repository, never()).deleteAllByControlEtecId(CONTROL_ID);
    }

    @Test
    void rechazaElRangoInvertido() {
        conPeriodo();
        conCategoriaHoras();

        assertThatThrownBy(() -> useCase.reemplazar(2026, 11,
                List.of(tramo("10", "5", "0", "0"))))
                .isInstanceOf(EscalaHoraEtecException.class)
                .hasMessageContaining("termina antes de empezar");
    }

    @Test
    void rechazaImportesNegativos() {
        conPeriodo();
        conCategoriaHoras();

        assertThatThrownBy(() -> useCase.reemplazar(2026, 11,
                List.of(tramo("0", "12", "-1", "0"))))
                .isInstanceOf(EscalaHoraEtecException.class)
                .hasMessageContaining("monto fijo");

        assertThatThrownBy(() -> useCase.reemplazar(2026, 11,
                List.of(tramo("0", "12", "0", "-1"))))
                .isInstanceOf(EscalaHoraEtecException.class)
                .hasMessageContaining("valor por hora");
    }

    @Test
    void rechazaTramosSinHoras() {
        conPeriodo();

        assertThatThrownBy(() -> useCase.reemplazar(2026, 11,
                List.of(tramo(null, "12", "0", "0"))))
                .isInstanceOf(EscalaHoraEtecException.class)
                .hasMessageContaining("horas desde y hasta");
    }

    @Test
    void rechazaTramoSinCategoria() {
        conPeriodo();

        EscalaHoraEtec sinCategoria = EscalaHoraEtec.builder()
                .categoriaId(null)
                .horasDesde(BigDecimal.ZERO)
                .horasHasta(new BigDecimal("12"))
                .montoFijo(BigDecimal.ZERO)
                .valorHora(BigDecimal.ZERO)
                .build();

        assertThatThrownBy(() -> useCase.reemplazar(2026, 11, List.of(sinCategoria)))
                .isInstanceOf(EscalaHoraEtecException.class)
                .hasMessageContaining("categoría");

        verify(repository, never()).deleteAllByControlEtecId(CONTROL_ID);
    }

    @Test
    void rechazaCategoriaNoMarcadaComoHorasEtec() {
        conPeriodo();
        when(categoriaRepository.findByCategoriaId(2002)).thenReturn(Optional.of(
                Categoria.builder()
                        .categoriaId(2002)
                        .nombre("CATEGORIA COMUN")
                        .etecHoras((byte) 0)
                        .build()));

        assertThatThrownBy(() -> useCase.reemplazar(2026, 11,
                List.of(tramo("0", "12", "0", "0"))))
                .isInstanceOf(EscalaHoraEtecException.class)
                .hasMessageContaining("no está marcada como horas ETEC");

        verify(repository, never()).deleteAllByControlEtecId(CONTROL_ID);
    }

    @Test
    void fallaSiElPeriodoNoTieneControlEtec() {
        when(controlEtecRepository.findByPeriodo(2026, 11)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> useCase.reemplazar(2026, 11, List.of()))
                .isInstanceOf(EscalaHoraEtecPeriodoException.class)
                .hasMessageContaining("2026/11");

        verify(repository, never()).deleteAllByControlEtecId(CONTROL_ID);
    }
}
