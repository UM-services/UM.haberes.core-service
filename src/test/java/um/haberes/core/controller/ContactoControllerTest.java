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
import um.haberes.core.exception.ContactoException;
import um.haberes.core.model.ContactoEntity;
import um.haberes.core.service.ContactoService;

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
class ContactoControllerTest {

    @Mock
    private ContactoService service;

    @InjectMocks
    private ContactoController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private ContactoEntity sampleContacto() {
        ContactoEntity contacto = new ContactoEntity();
        contacto.setLegajoId(1L);
        contacto.setFijo("1234-5678");
        contacto.setMovil("099-123-456");
        contacto.setMailPersonal("personal@example.com");
        contacto.setMailInstitucional("institucional@example.com");
        return contacto;
    }

    private String contactoJson() {
        return """
                {
                  "legajoId": 1,
                  "fijo": "1234-5678",
                  "movil": "099-123-456",
                  "mailPersonal": "personal@example.com",
                  "mailInstitucional": "institucional@example.com",
                  "created": null,
                  "updated": null
                }
                """;
    }

    @Test
    void findAll_returnsOkWithListOfContactos() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleContacto()));

        mockMvc.perform(get("/api/haberes/core/contacto/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "legajoId": 1,
                            "fijo": "1234-5678",
                            "movil": "099-123-456",
                            "mailPersonal": "personal@example.com",
                            "mailInstitucional": "institucional@example.com",
                            "created": null,
                            "updated": null
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findAllLegajos_returnsOkWithListOfContactos() throws Exception {
        when(service.findAllLegajos(List.of(1L, 2L))).thenReturn(List.of(sampleContacto()));

        mockMvc.perform(post("/api/haberes/core/contacto/legajos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[1,2]"))
                .andExpect(status().isOk())
                .andExpect(content().json("""
                        [
                          {
                            "legajoId": 1,
                            "fijo": "1234-5678",
                            "movil": "099-123-456",
                            "mailPersonal": "personal@example.com",
                            "mailInstitucional": "institucional@example.com",
                            "created": null,
                            "updated": null
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findByLegajoId_returnsOkWithContactoBody() throws Exception {
        when(service.findByLegajoId(1L)).thenReturn(sampleContacto());

        mockMvc.perform(get("/api/haberes/core/contacto/{legajoId}", 1L))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(contactoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByLegajoId_whenServiceThrowsContactoException_returnsBadRequest() throws Exception {
        when(service.findByLegajoId(99L)).thenThrow(new ContactoException(99L));

        mockMvc.perform(get("/api/haberes/core/contacto/{legajoId}", 99L))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_returnsOkWithContactoBody() throws Exception {
        when(service.add(any(ContactoEntity.class))).thenReturn(sampleContacto());

        mockMvc.perform(post("/api/haberes/core/contacto/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleContacto())))
                .andExpect(status().isOk())
                .andExpect(content().json(contactoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithContactoBody() throws Exception {
        when(service.update(any(ContactoEntity.class), eq(1L))).thenReturn(sampleContacto());

        mockMvc.perform(put("/api/haberes/core/contacto/{legajoId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleContacto())))
                .andExpect(status().isOk())
                .andExpect(content().json(contactoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void delete_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/contacto/{legajoId}", 1L))
                .andExpect(status().isNoContent());
    }
}
