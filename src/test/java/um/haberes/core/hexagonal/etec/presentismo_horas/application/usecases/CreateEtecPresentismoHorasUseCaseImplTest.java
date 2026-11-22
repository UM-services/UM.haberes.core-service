package um.haberes.core.hexagonal.etec.presentismo_horas.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.cursos.curso.domain.model.Curso;
import um.haberes.core.hexagonal.cursos.curso_cargo.application.service.CursoCargoService;
import um.haberes.core.hexagonal.cursos.curso_cargo.domain.model.CursoCargo;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.exception.EtecPresentismoHorasConflictoException;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.exception.EtecPresentismoHorasException;
import um.haberes.core.hexagonal.etec.presentismo_horas.application.service.EtecPresentismoHorasValidator;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.model.EtecPresentismoHoras;
import um.haberes.core.hexagonal.etec.presentismo_horas.domain.ports.out.EtecPresentismoHorasRepository;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.exception.AcreditacionException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.service.AcreditacionService;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;

@ExtendWith(MockitoExtension.class)
class CreateEtecPresentismoHorasUseCaseImplTest {

    @Mock
    private EtecPresentismoHorasRepository repository;

    @Mock
    private CursoCargoService cursoCargoService;

    @Mock
    private AcreditacionService acreditacionService;

    private EtecPresentismoHorasValidator validator;

    private CreateEtecPresentismoHorasUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        validator = new EtecPresentismoHorasValidator(repository, cursoCargoService, acreditacionService);
        useCase = new CreateEtecPresentismoHorasUseCaseImpl(repository, validator);
    }

    private CursoCargo curso(Integer facultadId, String horas) {
        return CursoCargo.builder()
                .legajoId(100L)
                .horasSemanales(new BigDecimal(horas))
                .curso(Curso.builder().facultadId(facultadId).geograficaId(1).build())
                .build();
    }

    private EtecPresentismoHoras presentismo() {
        return EtecPresentismoHoras.builder()
                .legajoId(100L)
                .anho(2026)
                .mes(11)
                .motivo("  Ausencia injustificada  ")
                .build();
    }

    private void periodoAbierto() {
        when(acreditacionService.findByPeriodo(2026, 11))
                .thenThrow(new AcreditacionException(2026, 11));
    }

    @Test
    void tieneCargosHorasCuandoHayCursosEtecConHoras() {
        when(cursoCargoService.findAllByLegajo(100L, 2026, 11))
                .thenReturn(List.of(curso(15, "10.00"), curso(16, "8.00")));

        assertThat(validator.tieneCargosHoras(100L, 2026, 11)).isTrue();
    }

    @Test
    void noTieneCargosHorasSinCursosEtecOConCeroHoras() {
        when(cursoCargoService.findAllByLegajo(100L, 2026, 11))
                .thenReturn(List.of(curso(16, "10.00"), curso(15, "0.00")));

        assertThat(validator.tieneCargosHoras(100L, 2026, 11)).isFalse();
    }

    @Test
    void creaLaPerdidaPorHorasConMotivoNormalizado() {
        when(cursoCargoService.findAllByLegajo(100L, 2026, 11))
                .thenReturn(List.of(curso(15, "10.00")));
        periodoAbierto();
        when(repository.existsByLegajoIdAndPeriodo(100L, 2026, 11)).thenReturn(false);
        when(repository.save(any(EtecPresentismoHoras.class))).thenAnswer(invocation -> {
            EtecPresentismoHoras item = invocation.getArgument(0);
            item.setPresentismoHorasId(7L);
            return item;
        });

        EtecPresentismoHoras created = useCase.createEtecPresentismoHoras(presentismo());

        assertThat(created.getPresentismoHorasId()).isEqualTo(7L);
        assertThat(created.getMotivo()).isEqualTo("Ausencia injustificada");
    }

    @Test
    void rechazaUnLegajoSinCursosEtec() {
        when(cursoCargoService.findAllByLegajo(100L, 2026, 11)).thenReturn(List.of());
        periodoAbierto();

        assertThatThrownBy(() -> useCase.createEtecPresentismoHoras(presentismo()))
                .isInstanceOf(EtecPresentismoHorasException.class)
                .hasMessageContaining("no tiene cargos ETEC por horas");
    }

    @Test
    void rechazaUnPeriodoAcreditado() {
        when(acreditacionService.findByPeriodo(2026, 11))
                .thenReturn(Acreditacion.builder().acreditado((byte) 1).build());

        assertThatThrownBy(() -> useCase.createEtecPresentismoHoras(presentismo()))
                .isInstanceOf(EtecPresentismoHorasConflictoException.class)
                .hasMessageContaining("acreditado");
    }

    @Test
    void rechazaUnaPerdidaYaRegistrada() {
        when(cursoCargoService.findAllByLegajo(100L, 2026, 11))
                .thenReturn(List.of(curso(15, "10.00")));
        periodoAbierto();
        when(repository.existsByLegajoIdAndPeriodo(100L, 2026, 11)).thenReturn(true);

        assertThatThrownBy(() -> useCase.createEtecPresentismoHoras(presentismo()))
                .isInstanceOf(EtecPresentismoHorasConflictoException.class)
                .hasMessageContaining("ya tiene registrada");
    }

    @Test
    void rechazaUnMotivoVacio() {
        EtecPresentismoHoras item = presentismo();
        item.setMotivo("   ");

        assertThatThrownBy(() -> useCase.createEtecPresentismoHoras(item))
                .isInstanceOf(EtecPresentismoHorasException.class)
                .hasMessageContaining("motivo es obligatorio");
    }
}
