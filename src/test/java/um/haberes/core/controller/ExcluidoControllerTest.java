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
import um.haberes.core.exception.ExcluidoException;
import um.haberes.core.model.ExcluidoEntity;
import um.haberes.core.service.ExcluidoService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ExcluidoControllerTest {

    @Mock
    private ExcluidoService service;

    @InjectMocks
    private ExcluidoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private ExcluidoEntity sampleExcluido() {
        ExcluidoEntity excluido = new ExcluidoEntity();
        excluido.setExcluidoId(9L);
        excluido.setLegajoId(123L);
        excluido.setAnho(2024);
        excluido.setMes(6);
        excluido.setObservaciones("Sin movimiento");
        return excluido;
    }

    private String excluidoJson() {
        return """
                {
                  "excluidoId": 9,
                  "legajoId": 123,
                  "anho": 2024,
                  "mes": 6,
                  "fecha": null,
                  "observaciones": "Sin movimiento",
                  "created": null,
                  "updated": null
                }
                """;
    }

    @Test
    void findAllByPeriodo_returnsOkWithListOfExcluidos() throws Exception {
        when(service.findAllByPeriodo(2024, 6)).thenReturn(List.of(sampleExcluido()));

        mockMvc.perform(get("/api/haberes/core/excluido/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + excluidoJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_returnsOkWithExcluido() throws Exception {
        when(service.findByUnique(123L, 2024, 6)).thenReturn(sampleExcluido());

        mockMvc.perform(get("/api/haberes/core/excluido/unique/{legajoId}/{anho}/{mes}", 123, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(excluidoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_whenServiceThrowsExcluidoException_returnsBadRequest() throws Exception {
        when(service.findByUnique(999L, 2024, 1)).thenThrow(new ExcluidoException(999L, 2024, 1));

        mockMvc.perform(get("/api/haberes/core/excluido/unique/{legajoId}/{anho}/{mes}", 999, 2024, 1))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_returnsOkWithSavedExcluido() throws Exception {
        ExcluidoEntity excluido = sampleExcluido();
        when(service.add(any(ExcluidoEntity.class))).thenReturn(excluido);

        mockMvc.perform(post("/api/haberes/core/excluido/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(excluido)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(excluidoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithUpdatedExcluido() throws Exception {
        ExcluidoEntity excluido = sampleExcluido();
        when(service.update(any(ExcluidoEntity.class), anyLong())).thenReturn(excluido);

        mockMvc.perform(put("/api/haberes/core/excluido/{excluidoId}", 9)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(excluido)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(excluidoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void deleteByUnique_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/excluido/unique/{legajoId}/{anho}/{mes}", 123, 2024, 6))
                .andExpect(status().isNoContent());
    }
}
