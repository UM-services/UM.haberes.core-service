package um.haberes.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import um.haberes.core.kotlin.model.Actividad;
import um.haberes.core.repository.ActividadRepository;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ActividadControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ActividadRepository actividadRepository;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testFindAllByLegajoId() throws Exception {
        // Setup
        Actividad actividad = new Actividad();
        actividad.setLegajoId(123L);
        actividad.setAnho(2025);
        actividad.setMes(7);
        actividad.setDocente((byte) 1);
        actividadRepository.save(actividad);

        // Execute & Verify
        mockMvc.perform(get("/api/haberes/core/actividad/legajo/{legajoId}", 123L))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].legajoId", is(123)))
                .andExpect(jsonPath("$[0].anho", is(2025)))
                .andExpect(jsonPath("$[0].mes", is(7)));
    }

    @Test
    void testAddActividad() throws Exception {
        // Setup
        Actividad newActividad = new Actividad();
        newActividad.setLegajoId(456L);
        newActividad.setAnho(2025);
        newActividad.setMes(8);
        newActividad.setDocente((byte) 0);
        newActividad.setClases((byte) 10);
        newActividad.setOtras((byte) 5);

        // Execute & Verify
        mockMvc.perform(post("/api/haberes/core/actividad/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newActividad)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.legajoId", is(456)))
                .andExpect(jsonPath("$.anho", is(2025)))
                .andExpect(jsonPath("$.mes", is(8)))
                .andExpect(jsonPath("$.actividadId").exists());
    }
}
