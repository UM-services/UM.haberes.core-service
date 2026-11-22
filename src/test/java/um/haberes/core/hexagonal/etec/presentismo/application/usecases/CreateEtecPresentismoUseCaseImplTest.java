package um.haberes.core.hexagonal.etec.presentismo.application.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.model.EtecAsignacionCargo;
import um.haberes.core.hexagonal.etec.asignacion_cargo.domain.ports.out.EtecAsignacionCargoRepository;
import um.haberes.core.hexagonal.etec.presentismo.application.exception.EtecPresentismoConflictoException;
import um.haberes.core.hexagonal.etec.presentismo.application.exception.EtecPresentismoException;
import um.haberes.core.hexagonal.etec.presentismo.application.service.EtecPresentismoValidator;
import um.haberes.core.hexagonal.etec.presentismo.domain.model.EtecPresentismo;
import um.haberes.core.hexagonal.etec.presentismo.domain.ports.out.EtecPresentismoRepository;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.exception.AcreditacionException;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.application.service.AcreditacionService;
import um.haberes.core.hexagonal.liquidaciones.acreditacion.domain.model.Acreditacion;

@ExtendWith(MockitoExtension.class)
class CreateEtecPresentismoUseCaseImplTest {

    @Mock
    private EtecPresentismoRepository repository;

    @Mock
    private EtecAsignacionCargoRepository asignacionRepository;

    @Mock
    private AcreditacionService acreditacionService;

    private EtecPresentismoValidator validator;

    private CreateEtecPresentismoUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        validator = new EtecPresentismoValidator(repository, asignacionRepository, acreditacionService);
        useCase = new CreateEtecPresentismoUseCaseImpl(repository, validator);
    }

    private EtecAsignacionCargo asignacion() {
        return EtecAsignacionCargo.builder()
                .asignacionCargoId(12L)
                .legajoId(100L)
                .categoriaId(992)
                .dependenciaId(7)
                .build();
    }

    private EtecPresentismo presentismo() {
        return EtecPresentismo.builder()
                .asignacionCargoId(12L)
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
    void creaLaPerdidaDePresentismoConMotivoNormalizado() {
        when(asignacionRepository.findById(12L)).thenReturn(Optional.of(asignacion()));
        when(asignacionRepository.findAllVigentesByLegajoAndPeriodo(100L, 2026, 11))
                .thenReturn(List.of(asignacion()));
        periodoAbierto();
        when(repository.existsByAsignacionCargoIdAndPeriodo(12L, 2026, 11)).thenReturn(false);
        when(repository.save(any(EtecPresentismo.class))).thenAnswer(invocation -> {
            EtecPresentismo item = invocation.getArgument(0);
            item.setPresentismoId(5L);
            return item;
        });

        EtecPresentismo created = useCase.createEtecPresentismo(presentismo());

        assertThat(created.getPresentismoId()).isEqualTo(5L);
        assertThat(created.getMotivo()).isEqualTo("Ausencia injustificada");
    }

    @Test
    void rechazaUnPeriodoAcreditado() {
        when(acreditacionService.findByPeriodo(2026, 11))
                .thenReturn(Acreditacion.builder().acreditado((byte) 1).build());

        assertThatThrownBy(() -> useCase.createEtecPresentismo(presentismo()))
                .isInstanceOf(EtecPresentismoConflictoException.class)
                .hasMessageContaining("acreditado");
    }

    @Test
    void rechazaUnaAsignacionQueNoEstaVigenteEnElPeriodo() {
        when(asignacionRepository.findById(12L)).thenReturn(Optional.of(asignacion()));
        when(asignacionRepository.findAllVigentesByLegajoAndPeriodo(100L, 2026, 11))
                .thenReturn(List.of());
        periodoAbierto();

        assertThatThrownBy(() -> useCase.createEtecPresentismo(presentismo()))
                .isInstanceOf(EtecPresentismoException.class)
                .hasMessageContaining("no está vigente");
    }

    @Test
    void rechazaUnaPerdidaYaRegistradaParaLaMismaAsignacionYPeriodo() {
        when(asignacionRepository.findById(12L)).thenReturn(Optional.of(asignacion()));
        when(asignacionRepository.findAllVigentesByLegajoAndPeriodo(100L, 2026, 11))
                .thenReturn(List.of(asignacion()));
        periodoAbierto();
        when(repository.existsByAsignacionCargoIdAndPeriodo(12L, 2026, 11)).thenReturn(true);

        assertThatThrownBy(() -> useCase.createEtecPresentismo(presentismo()))
                .isInstanceOf(EtecPresentismoConflictoException.class)
                .hasMessageContaining("ya tiene registrada");
    }

    @Test
    void rechazaUnMotivoVacio() {
        EtecPresentismo item = presentismo();
        item.setMotivo("   ");

        assertThatThrownBy(() -> useCase.createEtecPresentismo(item))
                .isInstanceOf(EtecPresentismoException.class)
                .hasMessageContaining("motivo es obligatorio");
    }
}
