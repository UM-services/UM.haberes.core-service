package um.haberes.core.controller;

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
import um.haberes.core.hexagonal.cursos.cargo_tipo.application.exception.CargoTipoException;
import um.haberes.core.hexagonal.cursos.cargo_tipo.application.service.CargoTipoService;
import um.haberes.core.hexagonal.cursos.cargo_tipo.domain.model.CargoTipo;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.web.controller.CargoTipoController;
import um.haberes.core.hexagonal.cursos.cargo_tipo.infrastructure.web.mapper.CargoTipoDtoMapper;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CargoTipoControllerTest {

    @Mock
    private CargoTipoService service;

    @InjectMocks
    private CargoTipoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new CargoTipoController(service, new CargoTipoDtoMapper());
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CargoTipo sampleCargoTipo() {
        CargoTipo cargoTipo = new CargoTipo();
        cargoTipo.setCargoTipoId(1);
        cargoTipo.setACargo((byte) 1);
        cargoTipo.setNombre("TITULAR");
        cargoTipo.setPrecedencia(10);
        return cargoTipo;
    }

    private String cargoTipoJson() {
        return """
                {
                  "cargoTipoId": 1,
                  "aCargo": 1,
                  "nombre": "TITULAR",
                  "precedencia": 10
                                                    }
                """;
    }

    @Test
    void findAll_returnsOkWithListOfCargoTipo() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleCargoTipo()));

        mockMvc.perform(get("/api/haberes/core/cargotipo/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [ {
                          "cargoTipoId": 1,
                          "aCargo": 1,
                          "nombre": "TITULAR",
                          "precedencia": 10
                                                                            } ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findByCargoTipoId_returnsOkWithCargoTipo() throws Exception {
        when(service.findByCargoTipoId(1)).thenReturn(sampleCargoTipo());

        mockMvc.perform(get("/api/haberes/core/cargotipo/{cargoTipoId}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(cargoTipoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByCargoTipoId_whenServiceThrowsCargoTipoException_returnsBadRequest() throws Exception {
        when(service.findByCargoTipoId(99)).thenThrow(new CargoTipoException(99));

        mockMvc.perform(get("/api/haberes/core/cargotipo/{cargoTipoId}", 99))
                .andExpect(status().isBadRequest());
    }
}
