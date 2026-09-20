package um.haberes.core.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.kotlin.model.Lectivo;
import um.haberes.core.service.LectivoService;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class LectivoControllerTest {

    @Mock
    private LectivoService service;

    @InjectMocks
    private LectivoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Lectivo sampleLectivo() {
        Lectivo lectivo = new Lectivo();
        lectivo.setLectivoId(1);
        lectivo.setNombre("Lectivo 2024");
        lectivo.setReducido(2024);
        return lectivo;
    }

    private String lectivoJson() {
        return """
                {
                  "lectivoId": 1,
                  "nombre": "Lectivo 2024",
                  "fechaInicio": null,
                  "fechaFinal": null,
                  "reducido": 2024,
                  "created": null,
                  "updated": null
                }
                """;
    }

    @Test
    void findAll_returnsOkWithListOfLectivos() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleLectivo()));

        mockMvc.perform(get("/api/haberes/core/lectivo/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + lectivoJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllReverse_returnsOkWithListOfLectivos() throws Exception {
        when(service.findAllReverse()).thenReturn(List.of(sampleLectivo()));

        mockMvc.perform(get("/api/haberes/core/lectivo/reverse"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + lectivoJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByLectivoId_returnsOkWithLectivo() throws Exception {
        when(service.findByLectivoId(1)).thenReturn(sampleLectivo());

        mockMvc.perform(get("/api/haberes/core/lectivo/{lectivoId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(lectivoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void saveAll_returnsOkWithSavedLectivos() throws Exception {
        Lectivo lectivo = sampleLectivo();
        when(service.saveAll(anyList())).thenReturn(List.of(lectivo));

        mockMvc.perform(put("/api/haberes/core/lectivo/saveall")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(List.of(lectivo))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + lectivoJson() + "]", JsonCompareMode.STRICT));
    }
}
