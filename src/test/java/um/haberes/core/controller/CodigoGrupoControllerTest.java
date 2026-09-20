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
import um.haberes.core.kotlin.model.CodigoGrupo;
import um.haberes.core.service.CodigoGrupoService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CodigoGrupoControllerTest {

    @Mock
    private CodigoGrupoService service;

    @InjectMocks
    private CodigoGrupoController controller;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private CodigoGrupo sampleCodigoGrupo() {
        CodigoGrupo codigoGrupo = new CodigoGrupo();
        codigoGrupo.setCodigoId(10);
        codigoGrupo.setRemunerativo((byte) 1);
        codigoGrupo.setNoRemunerativo((byte) 0);
        codigoGrupo.setDeduccion((byte) 0);
        codigoGrupo.setTotal((byte) 1);
        return codigoGrupo;
    }

    private String codigoGrupoJson() {
        return """
                {
                  "codigoId": 10,
                  "remunerativo": 1,
                  "noRemunerativo": 0,
                  "deduccion": 0,
                  "total": 1,
                  "codigo": null,
                  "created": null,
                  "updated": null
                }
                """;
    }

    private String codigoGrupoArrayJson() {
        return """
                [ {
                  "codigoId": 10,
                  "remunerativo": 1,
                  "noRemunerativo": 0,
                  "deduccion": 0,
                  "total": 1,
                  "codigo": null,
                  "created": null,
                  "updated": null
                } ]
                """;
    }

    @Test
    void findAll_returnsOkWithListOfCodigoGrupo() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleCodigoGrupo()));

        mockMvc.perform(get("/api/haberes/core/codigogrupo/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(codigoGrupoArrayJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByCodigoId_returnsOkWithCodigoGrupo() throws Exception {
        when(service.findByCodigoId(10)).thenReturn(sampleCodigoGrupo());

        mockMvc.perform(get("/api/haberes/core/codigogrupo/{codigoId}", 10))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoGrupoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByNoRemunerativo_returnsOkWithListOfCodigoGrupo() throws Exception {
        when(service.findAllByNoRemunerativo((byte) 0)).thenReturn(List.of(sampleCodigoGrupo()));

        mockMvc.perform(get("/api/haberes/core/codigogrupo/noremunerativo/{noRemunerativo}", 0))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoGrupoArrayJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByRemunerativo_returnsOkWithListOfCodigoGrupo() throws Exception {
        when(service.findAllByRemunerativo((byte) 1)).thenReturn(List.of(sampleCodigoGrupo()));

        mockMvc.perform(get("/api/haberes/core/codigogrupo/remunerativo/{remunerativo}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoGrupoArrayJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findAllByDeduccion_returnsOkWithListOfCodigoGrupo() throws Exception {
        when(service.findAllByDeduccion((byte) 0)).thenReturn(List.of(sampleCodigoGrupo()));

        mockMvc.perform(get("/api/haberes/core/codigogrupo/deduccion/{deduccion}", 0))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoGrupoArrayJson(), JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithCodigoGrupo() throws Exception {
        when(service.add(any(CodigoGrupo.class))).thenReturn(sampleCodigoGrupo());

        mockMvc.perform(post("/api/haberes/core/codigogrupo/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCodigoGrupo())))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoGrupoJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithCodigoGrupo() throws Exception {
        when(service.update(any(CodigoGrupo.class), anyInt())).thenReturn(sampleCodigoGrupo());

        mockMvc.perform(put("/api/haberes/core/codigogrupo/{codigoId}", 10)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleCodigoGrupo())))
                .andExpect(status().isOk())
                .andExpect(content().json(codigoGrupoJson(), JsonCompareMode.STRICT));
    }
}
