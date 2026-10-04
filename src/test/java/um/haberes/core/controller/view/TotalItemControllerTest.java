package um.haberes.core.controller.view;

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
import um.haberes.core.model.view.TotalItem;
import um.haberes.core.service.view.TotalItemService;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TotalItemControllerTest {

    @Mock
    private TotalItemService service;

    @InjectMocks
    private TotalItemController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private TotalItem sampleTotalItem() {
        TotalItem totalItem = new TotalItem();
        totalItem.setUniqueId("2024-6-100");
        totalItem.setAnho(2024);
        totalItem.setMes(6);
        totalItem.setCodigoId(100);
        totalItem.setTotal(new BigDecimal("1234.56"));
        return totalItem;
    }

    @Test
    void findAllByPeriodo_returnsOkWithTotalItemListBody() throws Exception {
        when(service.findAllByPeriodo(2024, 6)).thenReturn(List.of(sampleTotalItem()));

        mockMvc.perform(get("/api/haberes/core/totalitem/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        [
                          {
                            "uniqueId": "2024-6-100",
                            "anho": 2024,
                            "mes": 6,
                            "codigoId": 100,
                            "total": 1234.56
                          }
                        ]
                        """, JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_returnsOkWithTotalItemBody() throws Exception {
        when(service.findByUnique(2024, 6, 100)).thenReturn(sampleTotalItem());

        mockMvc.perform(get("/api/haberes/core/totalitem/unique/{anho}/{mes}/{codigoId}", 2024, 6, 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("""
                        {
                          "uniqueId": "2024-6-100",
                          "anho": 2024,
                          "mes": 6,
                          "codigoId": 100,
                          "total": 1234.56
                        }
                        """, JsonCompareMode.STRICT));
    }
}
