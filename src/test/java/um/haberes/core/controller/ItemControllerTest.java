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
import um.haberes.core.hexagonal.liquidaciones.codigo.domain.model.Codigo;
import um.haberes.core.hexagonal.liquidaciones.afip_concepto_sueldo.infrastructure.web.mapper.AfipConceptoSueldoDtoMapper;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.mapper.CodigoDtoMapper;
import um.haberes.core.hexagonal.liquidaciones.item.application.exception.ItemException;
import um.haberes.core.hexagonal.liquidaciones.item.application.service.ItemService;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.web.controller.ItemController;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.web.dto.ItemResponse;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.web.mapper.ItemDtoMapper;
import um.haberes.core.hexagonal.facultad.infrastructure.web.mapper.FacultadDtoMapper;
import um.haberes.core.hexagonal.geografica.infrastructure.web.mapper.GeograficaDtoMapper;
import um.haberes.core.hexagonal.personas.dependencia.infrastructure.web.mapper.DependenciaDtoMapper;
import um.haberes.core.hexagonal.personas.persona.domain.model.Persona;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.mapper.PersonaDtoMapper;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ItemControllerTest {

    @Mock
    private ItemService service;

    @InjectMocks
    private ItemController controller;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        controller = new ItemController(service, new ItemDtoMapper(new CodigoDtoMapper(new AfipConceptoSueldoDtoMapper()),
                new PersonaDtoMapper(new DependenciaDtoMapper(new FacultadDtoMapper(), new GeograficaDtoMapper()))));
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    private Item sampleItem() {
        Item item = new Item();
        item.setItemId(7L);
        item.setLegajoId(123L);
        item.setAnho(2024);
        item.setMes(6);
        item.setCodigoId(5);
        item.setCodigoNombre("Básico");
        item.setImporte(new BigDecimal("100.50"));
        return item;
    }

    private String itemJson() {
        return """
                {
                  "itemId": 7,
                  "legajoId": 123,
                  "anho": 2024,
                  "mes": 6,
                  "codigoId": 5,
                  "codigoNombre": "Básico",
                  "importe": 100.50,
                  "codigo": null,
                  "persona": null
                                                    }
                """;
    }

    @Test
    void toResponse_includesNestedCodigoAndPersonaForLegacyClients() {
        Item item = sampleItem();
        item.setCodigo(Codigo.builder().codigoId(5).nombre("Básico").build());
        item.setPersona(Persona.builder().legajoId(123L).apellido("Perez").nombre("Juan").build());

        ItemDtoMapper mapper = new ItemDtoMapper(new CodigoDtoMapper(new AfipConceptoSueldoDtoMapper()),
                new PersonaDtoMapper(new DependenciaDtoMapper(new FacultadDtoMapper(), new GeograficaDtoMapper())));
        ItemResponse response = mapper.toResponse(item);

        assertNotNull(response.getCodigo());
        assertEquals("Básico", response.getCodigo().getNombre());
        assertNotNull(response.getPersona());
        assertEquals(123L, response.getPersona().getLegajoId());
    }

    @Test
    void findAllByLegajo_returnsOkWithListOfItems() throws Exception {
        when(service.getItemsByLegajo(123L, 2024, 6)).thenReturn(List.of(sampleItem()));

        mockMvc.perform(get("/api/haberes/core/item/legajo/{legajoId}/{anho}/{mes}", 123, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + itemJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByCodigoId_returnsOkWithListOfItems() throws Exception {
        when(service.getItemsByCodigo(5, 2024, 6)).thenReturn(List.of(sampleItem()));

        mockMvc.perform(get("/api/haberes/core/item/codigo/{codigoId}/{anho}/{mes}", 5, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + itemJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByPeriodo_returnsOkWithListOfItems() throws Exception {
        when(service.getItemsByPeriodo(2024, 6, 100)).thenReturn(List.of(sampleItem()));

        mockMvc.perform(get("/api/haberes/core/item/periodo/{anho}/{mes}/{limit}", 2024, 6, 100))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + itemJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByPeriodo_whenLimitIsZero_usesDefaultLimitOf99999() throws Exception {
        when(service.getItemsByPeriodo(2024, 6, 99999)).thenReturn(List.of());

        mockMvc.perform(get("/api/haberes/core/item/periodo/{anho}/{mes}/{limit}", 2024, 6, 0))
                .andExpect(status().isOk())
                .andExpect(content().json("[]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByPeriodoAndLegajo_returnsOkWithListOfItems() throws Exception {
        when(service.getItemsByPeriodoAndLegajo(2024, 6, 123L, 50)).thenReturn(List.of(sampleItem()));

        mockMvc.perform(get("/api/haberes/core/item/periodolegajo/{anho}/{mes}/{legajoId}/{limit}", 2024, 6, 123, 50))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + itemJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void findAllByPeriodoAndLegajo_whenLimitIsZero_usesDefaultLimitOf99999() throws Exception {
        when(service.getItemsByPeriodoAndLegajo(2024, 6, 123L, 99999)).thenReturn(List.of());

        mockMvc.perform(get("/api/haberes/core/item/periodolegajo/{anho}/{mes}/{legajoId}/{limit}", 2024, 6, 123, 0))
                .andExpect(status().isOk())
                .andExpect(content().json("[]", JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_returnsOkWithItem() throws Exception {
        when(service.findByUnique(123L, 2024, 6, 5)).thenReturn(sampleItem());

        mockMvc.perform(get("/api/haberes/core/item/unique/{legajoId}/{anho}/{mes}/{codigoId}", 123, 2024, 6, 5))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(itemJson(), JsonCompareMode.STRICT));
    }

    @Test
    void findByUnique_whenServiceThrowsItemException_returnsBadRequest() throws Exception {
        when(service.findByUnique(999L, 2024, 6, 5)).thenThrow(new ItemException(999L, 2024, 6, 5));

        mockMvc.perform(get("/api/haberes/core/item/unique/{legajoId}/{anho}/{mes}/{codigoId}", 999, 2024, 6, 5))
                .andExpect(status().isBadRequest());
    }

    @Test
    void add_returnsCreatedWithSavedItem() throws Exception {
        Item item = sampleItem();
        when(service.add(any(Item.class))).thenReturn(item);

        mockMvc.perform(post("/api/haberes/core/item/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(item)))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(itemJson(), JsonCompareMode.STRICT));
    }

    @Test
    void update_returnsOkWithUpdatedItem() throws Exception {
        Item item = sampleItem();
        when(service.update(any(Item.class), anyLong())).thenReturn(item);

        mockMvc.perform(put("/api/haberes/core/item/{itemId}", 7)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(item)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json(itemJson(), JsonCompareMode.STRICT));
    }

    @Test
    void saveAll_returnsOkWithSavedItems() throws Exception {
        Item item = sampleItem();
        when(service.saveAllItems(anyList())).thenReturn(List.of(item));

        mockMvc.perform(put("/api/haberes/core/item/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(List.of(item))))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().json("[" + itemJson() + "]", JsonCompareMode.STRICT));
    }

    @Test
    void deleteByPeriodo_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/item/periodo/{anho}/{mes}", 2024, 6))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteAllByZero_returnsNoContent() throws Exception {
        mockMvc.perform(delete("/api/haberes/core/item/legajo/{legajoId}/{anho}/{mes}/deleteallbyzero", 123, 2024, 6))
                .andExpect(status().isNoContent());
    }

    @Test
    void onlyETEC_returnsOkWithBoolean() throws Exception {
        when(service.onlyETEC(123L, 2024, 6)).thenReturn(Boolean.TRUE);

        mockMvc.perform(get("/api/haberes/core/item/onlyETEC/{legajoId}/{anho}/{mes}", 123, 2024, 6))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(content().string("true"));
    }
}
