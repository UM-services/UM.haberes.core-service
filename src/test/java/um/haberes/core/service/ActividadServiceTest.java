package um.haberes.core.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import um.haberes.core.kotlin.model.Actividad;
import um.haberes.core.repository.ActividadRepository;
import um.haberes.core.repository.view.ActividadPeriodoRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActividadServiceTest {

    @Mock
    private ActividadRepository repository;

    @Mock
    private ActividadPeriodoRepository actividadPeriodoRepository;

    @InjectMocks
    private ActividadService actividadService;

    @Test
    void testFindAllByLegajoId() {
        // Setup
        Actividad actividad = new Actividad();
        actividad.setLegajoId(123L);
        actividad.setAnho(2025);
        actividad.setMes(7);

        when(repository.findAllByLegajoId(any(Long.class), any(Sort.class)))
                .thenReturn(List.of(actividad));

        // Execute
        List<Actividad> result = actividadService.findAllByLegajoId(123L);

        // Verify
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getLegajoId()).isEqualTo(123L);
        verify(repository).findAllByLegajoId(any(Long.class), any(Sort.class));
    }

    @Test
    void testAdd() {
        // Setup
        Actividad actividadToSave = new Actividad();
        actividadToSave.setLegajoId(456L);

        Actividad savedActividad = new Actividad();
        savedActividad.setActividadId(1L);
        savedActividad.setLegajoId(456L);

        when(repository.save(any(Actividad.class))).thenReturn(savedActividad);

        // Execute
        Actividad result = actividadService.add(actividadToSave);

        // Verify
        assertThat(result).isNotNull();
        assertThat(result.getActividadId()).isEqualTo(1L);
        assertThat(result.getLegajoId()).isEqualTo(456L);
        verify(repository).save(any(Actividad.class));
    }
    
    @Test
    void testUpdate() {
        // Setup
        Long actividadId = 1L;
        Actividad existingActividad = new Actividad();
        existingActividad.setActividadId(actividadId);
        existingActividad.setDocente((byte) 0);

        Actividad newActividadData = new Actividad();
        newActividadData.setDocente((byte) 1);

        when(repository.findById(actividadId)).thenReturn(Optional.of(existingActividad));
        when(repository.save(any(Actividad.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Execute
        Actividad result = actividadService.update(newActividadData, actividadId);

        // Verify
        assertThat(result).isNotNull();
        assertThat(result.getActividadId()).isEqualTo(actividadId);
        assertThat(result.getDocente()).isEqualTo((byte) 1);
        verify(repository).findById(actividadId);
        verify(repository).save(any(Actividad.class));
    }
}
