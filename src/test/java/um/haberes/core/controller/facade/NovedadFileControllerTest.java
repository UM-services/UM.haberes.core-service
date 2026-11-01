package um.haberes.core.controller.facade;

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
import um.haberes.core.exception.common.ImportNewsException;
import um.haberes.core.exception.common.TituloNotFoundException;
import um.haberes.core.service.facade.NovedadFileService;
import um.haberes.core.util.transfer.FileInfo;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class NovedadFileControllerTest {

    @Mock
    private NovedadFileService service;

    @InjectMocks
    private NovedadFileController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void upload_returnsOkWithServiceResponse() throws Exception {
        FileInfo fileInfo = new FileInfo("novedades.xlsx", "YWJjZGVm");
        when(service.upload(fileInfo, 2024, 6, true)).thenReturn("Upload ok");

        mockMvc.perform(post("/api/haberes/core/novedadfile/upload/{anho}/{mes}/{stepByStep}", 2024, 6, true)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(fileInfo)))
                .andExpect(status().isOk())
                .andExpect(content().string("Upload ok"));
    }

    @Test
    void upload_whenTituloNotFound_returnsBadRequest() throws Exception {
        when(service.upload(any(FileInfo.class), anyInt(), anyInt(), anyBoolean()))
                .thenThrow(new TituloNotFoundException("titulo no encontrado"));

        mockMvc.perform(post("/api/haberes/core/novedadfile/upload/{anho}/{mes}/{stepByStep}", 2024, 6, false)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(new FileInfo("novedades.xlsx", "YWJjZGVm"))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void importNews_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/novedadfile/import/{anho}/{mes}", 2024, 6))
                .andExpect(status().isNoContent());
    }

    @Test
    void importNews_whenImportNewsException_propagatesUnhandled() throws Exception {
        doThrow(new ImportNewsException(100L, 2024, 6, 5, 1)).when(service).importNews(2024, 6);

        Exception thrown = assertThrows(Exception.class, () -> mockMvc.perform(
                get("/api/haberes/core/novedadfile/import/{anho}/{mes}", 2024, 6)));
        boolean found = false;
        for (Throwable t = thrown; t != null && !found; t = t.getCause()) {
            found = t instanceof ImportNewsException;
        }
        assertTrue(found, "ImportNewsException should propagate because service call is outside the try block");
    }

    @Test
    void transfer_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/novedadfile/transfer/{anho}/{mes}", 2024, 6))
                .andExpect(status().isNoContent());
    }
}
