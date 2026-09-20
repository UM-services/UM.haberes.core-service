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
import um.haberes.core.hexagonal.facultad.domain.model.Facultad;
import um.haberes.core.kotlin.model.Categoria;
import um.haberes.core.kotlin.model.Dependencia;
import um.haberes.core.kotlin.model.dto.FormularioAsignacionCargo;
import um.haberes.core.service.dto.FormularioAsignacionCargoService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class FormularioAsignacionCargoControllerTest {

    @Mock
    private FormularioAsignacionCargoService service;

    @InjectMocks
    private FormularioAsignacionCargoController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Categoria sampleCategoria() {
        Categoria categoria = new Categoria();
        categoria.setCategoriaId(1);
        categoria.setNombre("Gabinete de Asesores");
        categoria.setBasico(new BigDecimal("1.00"));
        categoria.setDocente((byte) 1);
        categoria.setNoDocente((byte) 0);
        categoria.setLiquidaPorHora((byte) 0);
        categoria.setEstadoDocente(new BigDecimal("35.00"));
        return categoria;
    }

    private Dependencia sampleDependencia() {
        Dependencia dependencia = new Dependencia();
        dependencia.setDependenciaId(10);
        dependencia.setNombre("Secretaria General");
        dependencia.setAcronimo("SECG");
        dependencia.setFacultadId(1);
        dependencia.setGeograficaId(2);
        return dependencia;
    }

    private Facultad sampleFacultad() {
        return Facultad.builder()
                .facultadId(1)
                .nombre("Facultad de Ingenieria")
                .reducido("FING")
                .server("srv")
                .backendServer("be")
                .backendPort(8080)
                .dbName("db")
                .dsn("dsn")
                .build();
    }

    private FormularioAsignacionCargo sampleFormulario() {
        return new FormularioAsignacionCargo(
                List.of(sampleCategoria()),
                List.of(sampleCategoria()),
                List.of(sampleDependencia()),
                List.of(sampleFacultad()));
    }

    @Test
    void findData_returnsOkWithFormularioBody() throws Exception {
        when(service.findData()).thenReturn(sampleFormulario());

        mockMvc.perform(get("/api/haberes/core/formularioAsignacionCargo/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "categorias": [
                            {
                              "categoriaId": 1,
                              "nombre": "Gabinete de Asesores",
                              "basico": 1.00,
                              "docente": 1,
                              "noDocente": 0,
                              "liquidaPorHora": 0,
                              "estadoDocente": 35.00,
                              "created": null,
                              "updated": null
                            }
                          ],
                          "categoriasAsignables": [
                            {
                              "categoriaId": 1,
                              "nombre": "Gabinete de Asesores",
                              "basico": 1.00,
                              "docente": 1,
                              "noDocente": 0,
                              "liquidaPorHora": 0,
                              "estadoDocente": 35.00,
                              "created": null,
                              "updated": null
                            }
                          ],
                          "dependencias": [
                            {
                              "dependenciaId": 10,
                              "nombre": "Secretaria General",
                              "acronimo": "SECG",
                              "facultadId": 1,
                              "geograficaId": 2,
                              "facultad": null,
                              "geografica": null,
                              "created": null,
                              "updated": null,
                              "sedeKey": "1.2"
                            }
                          ],
                          "facultades": [
                            {
                              "facultadId": 1,
                              "nombre": "Facultad de Ingenieria",
                              "reducido": "FING",
                              "server": "srv",
                              "backendServer": "be",
                              "backendPort": 8080,
                              "dbName": "db",
                              "dsn": "dsn"
                            }
                          ]
                        }
                        """, JsonCompareMode.STRICT));
    }
}
