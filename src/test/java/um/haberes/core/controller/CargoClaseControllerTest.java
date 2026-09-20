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
import um.haberes.core.kotlin.model.CargoClase;
import um.haberes.core.service.CargoClaseService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CargoClaseControllerTest {

    @Mock
    private CargoClaseService service;

    @InjectMocks
    private CargoClaseController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CargoClase sampleCargoClase() {
        CargoClase cargoClase = new CargoClase();
        cargoClase.setCargoClaseId(1L);
        cargoClase.setNombre("Ayudante de primera");
        cargoClase.setClaseId(2);
        return cargoClase;
    }

    private static final String CARGO_CLASE_JSON = """
            {
              "cargoClaseId": 1,
              "nombre": "Ayudante de primera",
              "claseId": 2,
              "clase": null,
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findAll_returnsOkWithListOfCargoClase() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleCargoClase()));

        mockMvc.perform(get("/api/haberes/core/cargoclase/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + CARGO_CLASE_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findByCargoClaseId_returnsOkWithCargoClaseBody() throws Exception {
        when(service.findByCargoClaseId(1L)).thenReturn(sampleCargoClase());

        mockMvc.perform(get("/api/haberes/core/cargoclase/{cargoClaseId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(CARGO_CLASE_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithCargoClaseBody() throws Exception {
        when(service.add(any(CargoClase.class))).thenReturn(sampleCargoClase());

        mockMvc.perform(post("/api/haberes/core/cargoclase/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CargoClase())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(CARGO_CLASE_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithCargoClaseBody() throws Exception {
        when(service.update(any(CargoClase.class), anyLong())).thenReturn(sampleCargoClase());

        mockMvc.perform(put("/api/haberes/core/cargoclase/{cargoClaseId}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new CargoClase())))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(CARGO_CLASE_JSON, JsonCompareMode.STRICT));
    }
}
