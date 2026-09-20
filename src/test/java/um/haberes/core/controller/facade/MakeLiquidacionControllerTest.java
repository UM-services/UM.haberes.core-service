package um.haberes.core.controller.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.service.facade.MakeLiquidacionService;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MakeLiquidacionControllerTest {

    @Mock
    private MakeLiquidacionService service;

    @InjectMocks
    private MakeLiquidacionController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Item sampleItem() {
        Item item = new Item();
        item.setItemId(1L);
        item.setLegajoId(100L);
        item.setAnho(2024);
        item.setMes(6);
        item.setCodigoId(5);
        item.setCodigoNombre("Basico");
        item.setImporte(new BigDecimal("1500.25"));
        item.setCodigo(null);
        item.setPersona(samplePersona());
        return item;
    }

    private Persona samplePersona() {
        Persona persona = new Persona();
        persona.setLegajoId(100L);
        persona.setApellido("Perez");
        persona.setNombre("Juan");
        return persona;
    }

    @Test
    void liquidacionByLegajoId_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/makeLiquidacion/legajo/{legajoId}/{anho}/{mes}/{force}", 100L, 2024, 6, true))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteLiquidacionByLegajoId_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/makeLiquidacion/legajo/{legajoId}/{anho}/{mes}/{force}", 100L, 2024, 6, true))
                .andExpect(status().isNoContent());
    }

    @Test
    void basicoAndAntiguedad_returnsOkWithItemList() throws Exception {
        when(service.basicoAndAntiguedad(100L, 2024, 6)).thenReturn(List.of(sampleItem()));

        mockMvc.perform(get("/api/haberes/core/makeLiquidacion/basicoAndAntiguedad/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "itemId": 1,
                            "legajoId": 100,
                            "anho": 2024,
                            "mes": 6,
                            "codigoId": 5,
                            "codigoNombre": "Basico",
                            "importe": 1500.25,
                            "codigoIncluidoEtec": null,
                            "codigo": null,
                            "persona": {
                              "legajoId": 100,
                              "documento": 0,
                              "apellido": "Perez",
                              "nombre": "Juan",
                              "nacimiento": null,
                              "altaDocente": null,
                              "ajusteDocente": 0,
                              "altaAdministrativa": null,
                              "ajusteAdministrativo": 0,
                              "estadoCivil": "",
                              "situacionId": null,
                              "reemplazoDesarraigo": 0,
                              "mitadDesarraigo": 0,
                              "cuil": "",
                              "posgrado": 0,
                              "estado": 0,
                              "liquida": "",
                              "estadoAfip": 0,
                              "dependenciaId": null,
                              "dependencia": null,
                              "salida": null,
                              "obraSocial": null,
                              "actividadAfip": null,
                              "localidadAfip": null,
                              "situacionAfip": 0,
                              "modeloContratacionAfip": null,
                              "directivoEtec": 0,
                              "apellidoNombre": "Perez, Juan"
                            }
                                                                                  }
                        ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void generateCargosDocentes_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/makeLiquidacion/generateCargosDocentes/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isNoContent());
    }

    @Test
    void generateCargosNoDocentes_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/makeLiquidacion/generateCargosNoDocentes/{legajoId}/{anho}/{mes}", 100L, 2024, 6))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteNovedadDuplicada_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/makeLiquidacion/deleteNovedadDuplicada/{anho}/{mes}", 2024, 6))
                .andExpect(status().isNoContent());
    }

    @Test
    void desmarcaPeriodo_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/makeLiquidacion/desmarcaPeriodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isNoContent());
    }

    @Test
    void generateSIJP_returnsOkWithSijpAttachment() throws Exception {
        Path file = Files.createTempFile("sijp", ".txt");
        Files.writeString(file, "sijp content");
        when(service.generateSIJP(2024, 6)).thenReturn(file.toString());

        mockMvc.perform(get("/api/haberes/core/makeLiquidacion/generateSIJP/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=sijp.txt"));
    }
}
