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
import um.haberes.core.model.BuildEntity;
import um.haberes.core.service.BuildService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class BuildControllerTest {

    @Mock
    private BuildService service;

    private BuildController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new BuildController(service);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private BuildEntity sampleBuild() {
        BuildEntity build = new BuildEntity();
        build.setBuild(42L);
        return build;
    }

    @Test
    void findLast_returnsOkWithBuildBody() throws Exception {
        when(service.findLast()).thenReturn(sampleBuild());

        mockMvc.perform(get("/api/haberes/core/build/last"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "build": 42,
                          "created": null,
                          "updated": null
                        }
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void add_returnsOkWithBuildBody() throws Exception {
        when(service.add(any(BuildEntity.class))).thenReturn(sampleBuild());

        mockMvc.perform(post("/api/haberes/core/build/"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "build": 42,
                          "created": null,
                          "updated": null
                        }
                        """, JsonCompareMode.STRICT));
    }
}
