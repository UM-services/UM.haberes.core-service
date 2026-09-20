package um.haberes.core.controller;

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
import um.haberes.core.exception.UsuarioException;
import um.haberes.core.model.UsuarioEntity;
import um.haberes.core.service.UsuarioService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UsuarioControllerTest {

    @Mock
    private UsuarioService service;

    private UsuarioController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new UsuarioController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private UsuarioEntity sampleUsuario() {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setLegajoId(100L);
        usuario.setPassword("hash");
        usuario.setBuild(42L);
        usuario.setUsuarioId(9L);
        usuario.setFacultadId(1);
        return usuario;
    }

    private String usuarioBodyJson() throws Exception {
        return new ObjectMapper().writeValueAsString(sampleUsuario());
    }

    private static final String USUARIO_JSON = """
            {
              "legajoId": 100,
              "password": "hash",
              "lastLog": null,
              "build": 42,
              "usuarioId": 9,
              "facultadId": 1,
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findByLegajoId_returnsOkWithUsuarioBody() throws Exception {
        when(service.findByLegajoId(100L)).thenReturn(sampleUsuario());

        mockMvc.perform(get("/api/haberes/core/usuario/{legajoId}", 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(USUARIO_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void findByLegajoId_whenServiceThrowsUsuarioException_returnsBadRequest() throws Exception {
        when(service.findByLegajoId(99L)).thenThrow(new UsuarioException(99L));

        mockMvc.perform(get("/api/haberes/core/usuario/{legajoId}", 99))
                .andExpect(status().isBadRequest());
    }

    @Test
    void updateLastLog_returnsOkWithUsuarioBody() throws Exception {
        when(service.updateLastLog(100L, 42L)).thenReturn(sampleUsuario());

        mockMvc.perform(get("/api/haberes/core/usuario/lastlog/{legajoId}/{build}", 100, 42))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(USUARIO_JSON, JsonCompareMode.STRICT));
    }

    @Test
    void updateLastLog_whenServiceThrowsUsuarioException_returnsBadRequest() throws Exception {
        when(service.updateLastLog(99L, 42L)).thenThrow(new UsuarioException(99L));

        mockMvc.perform(get("/api/haberes/core/usuario/lastlog/{legajoId}/{build}", 99, 42))
                .andExpect(status().isBadRequest());
    }

    @Test
    void isUserValid_returnsOkWithTrueBody() throws Exception {
        when(service.isUserValid(any(UsuarioEntity.class))).thenReturn(Boolean.TRUE);

        mockMvc.perform(put("/api/haberes/core/usuario/isuservalid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioBodyJson()))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    void setPassword_returnsNoContent() throws Exception {
        mockMvc.perform(put("/api/haberes/core/usuario/setpassword")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(usuarioBodyJson()))
                .andExpect(status().isNoContent())
                .andExpect(content().string(""));
    }
}
