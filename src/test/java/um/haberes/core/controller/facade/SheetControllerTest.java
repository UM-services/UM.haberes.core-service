package um.haberes.core.controller.facade;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import um.haberes.core.service.facade.SheetService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class SheetControllerTest {

    @Mock
    private SheetService service;

    @InjectMocks
    private SheetController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private String existingTempFilePath() throws IOException {
        Path file = Files.createTempFile("sheet-test", ".xlsx");
        Files.write(file, new byte[]{1, 2, 3, 4});
        file.toFile().deleteOnExit();
        return file.toAbsolutePath().toString();
    }

    @Test
    void generatecargos_returnsOkWithXlsxAttachment() throws Exception {
        when(service.generateCargos(2024, 6)).thenReturn(existingTempFilePath());

        mockMvc.perform(get("/api/haberes/core/sheet/generatecargos/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cargos.xlsx"));
    }

    @Test
    void generateitems_returnsOkWithXlsxAttachment() throws Exception {
        when(service.generateItems(2024, 6)).thenReturn(existingTempFilePath());

        mockMvc.perform(get("/api/haberes/core/sheet/generateitems/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=items.xlsx"));
    }

    @Test
    void generatecodigosnodoc_returnsOkWithXlsxAttachment() throws Exception {
        when(service.generateCodigosNoDoc(2024, 6)).thenReturn(existingTempFilePath());

        mockMvc.perform(get("/api/haberes/core/sheet/generatecodigosnodoc/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=codigos.xlsx"));
    }

    @Test
    void generatecategoriasnodoc_returnsOkWithXlsxAttachment() throws Exception {
        when(service.generateCategoriasNoDoc(2024, 6)).thenReturn(existingTempFilePath());

        mockMvc.perform(get("/api/haberes/core/sheet/generatecategoriasnodoc/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=categorias.xlsx"));
    }

    @Test
    void comparativocodigo_returnsOkWithXlsxAttachment() throws Exception {
        when(service.comparativoCodigo(5, 2024, 6)).thenReturn(existingTempFilePath());

        mockMvc.perform(get("/api/haberes/core/sheet/comparativocodigo/{codigoId}/{anho}/{mes}", 5, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=comparativo.xlsx"));
    }

    @Test
    void simulasac_returnsOkWithXlsxAttachment() throws Exception {
        when(service.simulasac(2024, 1)).thenReturn(existingTempFilePath());

        mockMvc.perform(get("/api/haberes/core/sheet/simulasac/{anho}/{semestre}", 2024, 1))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=simulasac.xlsx"));
    }

    @Test
    void generasac_returnsNoContent() throws Exception {
        mockMvc.perform(get("/api/haberes/core/sheet/generasac/{anho}/{semestre}/{adelanto}", 2024, 1, 1))
                .andExpect(status().isNoContent());
    }

    @Test
    void generateLegajoCursoCantidad_returnsOkWithXlsxAttachment() throws Exception {
        when(service.generateLegajoCursoCantidad(2024, 6)).thenReturn(existingTempFilePath());

        mockMvc.perform(get("/api/haberes/core/sheet/generatelegajocursocantidad/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=docentes.xlsx"));
    }

    @Test
    void generateLiquidables_returnsOkWithXlsxAttachment() throws Exception {
        when(service.generateLiquidables()).thenReturn(existingTempFilePath());

        mockMvc.perform(get("/api/haberes/core/sheet/generateLiquidables"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=liquidables.xlsx"));
    }

    @Test
    void generateCategorias_returnsOkWithXlsxAttachment() throws Exception {
        when(service.generateCategorias()).thenReturn(existingTempFilePath());

        mockMvc.perform(get("/api/haberes/core/sheet/generateCategorias"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=categorias.xlsx"));
    }

    @Test
    void generateBasicos_returnsOkWithXlsxAttachment() throws Exception {
        when(service.generateBasicos(2024, 6)).thenReturn(existingTempFilePath());

        mockMvc.perform(get("/api/haberes/core/sheet/generateBasicos/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=basicos.xlsx"));
    }

    @Test
    void generatePersonales_returnsOkWithXlsxAttachment() throws Exception {
        when(service.generatePersonales()).thenReturn(existingTempFilePath());

        mockMvc.perform(get("/api/haberes/core/sheet/generatePersonales"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=personales.xlsx"));
    }

    @Test
    void comparaConsecutivos_returnsOkWithXlsxAttachment() throws Exception {
        when(service.comparaConsecutivos(2024, 6)).thenReturn(existingTempFilePath());

        mockMvc.perform(get("/api/haberes/core/sheet/comparaConsecutivo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=consecutivos.xlsx"));
    }

    @Test
    void comparaImputacionSueldos_returnsOkWithXlsxAttachment() throws Exception {
        when(service.comparaImputaciones(2024, 1, 2024, 6)).thenReturn(existingTempFilePath());

        mockMvc.perform(get("/api/haberes/core/sheet/comparaImputacionSueldos/{anhoDesde}/{mesDesde}/{anhoHasta}/{mesHasta}",
                        2024, 1, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=imputaciones.xlsx"));
    }

    @Test
    void cruceImputaciones_returnsOkWithXlsxAttachment() throws Exception {
        when(service.cruceImputaciones(2024, 6)).thenReturn(existingTempFilePath());

        mockMvc.perform(get("/api/haberes/core/sheet/cruceImputaciones/{anho}/{mes}", 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_OCTET_STREAM))
                .andExpect(header().string(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=cruce.xlsx"));
    }
}
