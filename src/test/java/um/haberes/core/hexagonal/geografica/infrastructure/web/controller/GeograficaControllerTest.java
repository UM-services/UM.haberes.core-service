package um.haberes.core.hexagonal.geografica.infrastructure.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.hexagonal.geografica.application.exception.GeograficaException;
import um.haberes.core.hexagonal.geografica.application.service.GeograficaService;
import um.haberes.core.hexagonal.geografica.domain.model.Geografica;
import um.haberes.core.hexagonal.geografica.infrastructure.web.dto.GeograficaRequest;
import um.haberes.core.hexagonal.geografica.infrastructure.web.mapper.GeograficaDtoMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class GeograficaControllerTest {

    @Mock
    private GeograficaService geograficaService;

    private GeograficaController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new GeograficaController(geograficaService, new GeograficaDtoMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Geografica sampleGeografica() {
        return Geografica.builder()
                .geograficaId(1)
                .nombre("Provincia de Buenos Aires")
                .reducido("PBA")
                .desarraigo(new BigDecimal("10.50"))
                .build();
    }

    private GeograficaRequest sampleRequest() {
        return GeograficaRequest.builder()
                .nombre("Provincia de Buenos Aires")
                .reducido("PBA")
                .desarraigo(new BigDecimal("10.50"))
                .build();
    }

    private final String expectedResponseJson = """
            {
              "geograficaId": 1,
              "nombre": "Provincia de Buenos Aires",
              "reducido": "PBA",
              "desarraigo": 10.50,
              "geograficaIdReemplazo": null
            }
            """;

    @Test
    void createGeografica_returnsCreatedWithMappedResponse() throws Exception {
        when(geograficaService.createGeografica(any(Geografica.class))).thenReturn(sampleGeografica());

        mockMvc.perform(post("/api/haberes/core/geografica")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleRequest())))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponseJson, JsonCompareMode.STRICT));
    }

    @Test
    void getGeograficaById_returnsOkWithMappedResponse() throws Exception {
        when(geograficaService.getGeograficaById(1)).thenReturn(sampleGeografica());

        mockMvc.perform(get("/api/haberes/core/geografica/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponseJson, JsonCompareMode.STRICT));
    }

    @Test
    void getGeograficaById_isAlsoExposedUnderShortAliasPath() throws Exception {
        when(geograficaService.getGeograficaById(1)).thenReturn(sampleGeografica());

        mockMvc.perform(get("/geografica/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponseJson, JsonCompareMode.STRICT));
    }

    @Test
    void getGeograficaById_whenNotFound_returnsNotFound() throws Exception {
        when(geograficaService.getGeograficaById(99)).thenThrow(new GeograficaException(99));

        mockMvc.perform(get("/api/haberes/core/geografica/{id}", 99))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAllGeograficas_returnsOkWithListOfMappedResponses() throws Exception {
        when(geograficaService.getAllGeograficas()).thenReturn(List.of(sampleGeografica()));

        mockMvc.perform(get("/api/haberes/core/geografica/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedResponseJson + "]", JsonCompareMode.STRICT));
    }

    @Test
    void getGeograficasByIds_returnsOkWithListOfMappedResponses() throws Exception {
        when(geograficaService.getGeograficasByIds(List.of(1))).thenReturn(List.of(sampleGeografica()));

        mockMvc.perform(post("/api/haberes/core/geografica/ids")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(List.of(1))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + expectedResponseJson + "]", JsonCompareMode.STRICT));
    }

    @Test
    void updateGeografica_returnsOkWithMappedResponse() throws Exception {
        when(geograficaService.updateGeografica(eq(1), any(Geografica.class))).thenReturn(sampleGeografica());

        mockMvc.perform(put("/api/haberes/core/geografica/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleRequest())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(expectedResponseJson, JsonCompareMode.STRICT));
    }

    @Test
    void updateGeografica_whenNotFound_returnsNotFound() throws Exception {
        when(geograficaService.updateGeografica(eq(99), any(Geografica.class)))
                .thenThrow(new GeograficaException(99));

        mockMvc.perform(put("/api/haberes/core/geografica/{id}", 99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleRequest())))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteGeografica_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/geografica/{id}", 1))
                .andExpect(status().isNoContent());
    }
}
