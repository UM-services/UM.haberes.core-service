package um.haberes.core.hexagonal.etec.catalogo.application.service;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.liquidaciones.codigo.application.service.CodigoService;
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;

@ExtendWith(MockitoExtension.class)
class EtecCatalogoValidatorTest {

    @Mock
    private CodigoService codigoService;

    @Test
    void noFallaConCatalogoCompleto() {
        when(codigoService.findAll()).thenReturn(codigos(984, 985, 986, 987, 988, 989, 990));

        assertThatCode(() -> new EtecCatalogoValidator(codigoService, true).run(null))
                .doesNotThrowAnyException();
    }

    @Test
    void enModoEstrictoFallaSiFaltanCodigos() {
        when(codigoService.findAll()).thenReturn(codigos(984, 985));

        assertThatThrownBy(() -> new EtecCatalogoValidator(codigoService, true).run(null))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("986")
                .hasMessageContaining("989")
                .hasMessageContaining("990");
    }

    @Test
    void enModoNoEstrictoSoloRegistraElError() {
        when(codigoService.findAll()).thenReturn(codigos(984, 985));

        assertThatCode(() -> new EtecCatalogoValidator(codigoService, false).run(null))
                .doesNotThrowAnyException();
    }

    private List<Codigo> codigos(Integer... ids) {
        return Arrays.stream(ids)
                .map(id -> Codigo.builder().codigoId(id).build())
                .toList();
    }
}
