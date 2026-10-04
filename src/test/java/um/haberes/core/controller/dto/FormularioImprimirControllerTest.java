package um.haberes.core.controller.dto;

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
import um.haberes.core.model.ContactoEntity;
import um.haberes.core.model.LegajoControlEntity;
import um.haberes.core.model.dto.FormularioImprimir;
import um.haberes.core.service.dto.FormularioImprimirService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FormularioImprimirControllerTest {

    @Mock
    private FormularioImprimirService service;

    @InjectMocks
    private FormularioImprimirController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private LegajoControlEntity sampleLegajoControl() {
        LegajoControlEntity legajoControl = new LegajoControlEntity();
        legajoControl.setLegajoControlId(1L);
        legajoControl.setLegajoId(100L);
        legajoControl.setAnho(2024);
        legajoControl.setMes(6);
        legajoControl.setLiquidado((byte) 1);
        legajoControl.setFusionado((byte) 0);
        legajoControl.setBonoEnviado((byte) 0);
        return legajoControl;
    }

    private ContactoEntity sampleContacto() {
        ContactoEntity contacto = new ContactoEntity();
        contacto.setLegajoId(100L);
        contacto.setFijo("44445555");
        contacto.setMovil("1155556666");
        contacto.setMailPersonal("jp@example.com");
        contacto.setMailInstitucional("jperez@um.example.edu");
        return contacto;
    }

    @Test
    void findData_returnsOkWithFormularioBody() throws Exception {
        FormularioImprimir formulario = new FormularioImprimir(
                List.of(sampleLegajoControl()), List.of(sampleContacto()));
        when(service.findData(2024, 6, 10, "todos")).thenReturn(formulario);

        mockMvc.perform(get("/api/haberes/core/formularioImprimir/{anho}/{mes}/{dependenciaId}/{filtro}",
                        2024, 6, 10, "todos"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "legajoControls": [
                            {
                              "legajoControlId": 1,
                              "legajoId": 100,
                              "anho": 2024,
                              "mes": 6,
                              "liquidado": 1,
                              "fusionado": 0,
                              "bonoEnviado": 0,
                              "persona": null,
                              "created": null,
                              "updated": null
                            }
                          ],
                          "contactos": [
                            {
                              "legajoId": 100,
                              "fijo": "44445555",
                              "movil": "1155556666",
                              "mailPersonal": "jp@example.com",
                              "mailInstitucional": "jperez@um.example.edu",
                              "created": null,
                              "updated": null
                            }
                          ]
                        }
                        """, JsonCompareMode.STRICT));
    }
}
