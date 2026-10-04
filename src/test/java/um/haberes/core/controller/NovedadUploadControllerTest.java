package um.haberes.core.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.json.JsonCompareMode;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.model.NovedadUploadEntity;
import um.haberes.core.service.NovedadUploadService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NovedadUploadControllerTest {

    @Mock
    private NovedadUploadService service;

    private NovedadUploadController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new NovedadUploadController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private NovedadUploadEntity sampleNovedadUpload() {
        NovedadUploadEntity upload = new NovedadUploadEntity();
        upload.setNovedadUploadId(2L);
        upload.setLegajoId(100L);
        upload.setAnho(2024);
        upload.setMes(6);
        upload.setCodigoId(10);
        upload.setDependenciaId(20);
        upload.setImporte(new BigDecimal("9.90"));
        upload.setValue("B");
        upload.setPendiente((byte) 1);
        return upload;
    }

    private static final String NOVEDAD_UPLOAD_JSON = """
            {
              "novedadUploadId": 2,
              "legajoId": 100,
              "anho": 2024,
              "mes": 6,
              "codigoId": 10,
              "dependenciaId": 20,
              "importe": 9.90,
              "value": "B",
              "pendiente": 1,
              "persona": null,
              "codigo": null,
              "dependencia": null,
              "created": null,
              "updated": null
            }
            """;

    @Test
    void findAllByPendiente_returnsOkWithListOfNovedadUpload() throws Exception {
        when(service.findAllByPendiente(2024, 6, (byte) 1)).thenReturn(List.of(sampleNovedadUpload()));

        mockMvc.perform(get("/api/haberes/core/novedadupload/pendiente/{anho}/{mes}/{pendiente}", 2024, 6, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + NOVEDAD_UPLOAD_JSON + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByPendiente_whenNoPending_returnsEmptyList() throws Exception {
        when(service.findAllByPendiente(2024, 6, (byte) 0)).thenReturn(List.of());

        mockMvc.perform(get("/api/haberes/core/novedadupload/pendiente/{anho}/{mes}/{pendiente}", 2024, 6, 0))
                .andExpect(status().isOk())
                .andExpect(content().json("[]", JsonCompareMode.STRICT));
    }
}
