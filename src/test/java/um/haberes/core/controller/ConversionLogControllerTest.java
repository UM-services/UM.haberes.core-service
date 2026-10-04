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
import um.haberes.core.model.ConversionLogEntity;
import um.haberes.core.service.ConversionLogService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ConversionLogControllerTest {

    @Mock
    private ConversionLogService service;

    @InjectMocks
    private ConversionLogController controller;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private ConversionLogEntity sampleConversionLog() {
        return new ConversionLogEntity(1L, 2024, 6);
    }

    private String conversionLogJson() {
        return """
                {
                  "version": 1,
                  "anho": 2024,
                  "mes": 6,
                  "created": null,
                  "updated": null
                }
                """;
    }

    @Test
    void findAll_returnsOkWithListOfConversionLogs() throws Exception {
        when(service.findAll()).thenReturn(List.of(sampleConversionLog()));

        mockMvc.perform(get("/api/haberes/core/conversionlog/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "version": 1,
                            "anho": 2024,
                            "mes": 6,
                            "created": null,
                            "updated": null
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithConversionLogBody() throws Exception {
        when(service.add(any(ConversionLogEntity.class))).thenReturn(sampleConversionLog());

        mockMvc.perform(post("/api/haberes/core/conversionlog/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(sampleConversionLog())))
                .andExpect(status().isOk())
                .andExpect(content().json(conversionLogJson(), JsonCompareMode.STRICT));
    }
}
