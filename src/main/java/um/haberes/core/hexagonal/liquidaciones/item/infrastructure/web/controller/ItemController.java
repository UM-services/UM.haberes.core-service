package um.haberes.core.hexagonal.liquidaciones.item.infrastructure.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.item.application.exception.ItemException;
import um.haberes.core.hexagonal.liquidaciones.item.application.service.ItemService;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.web.dto.ItemRequest;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.web.dto.ItemResponse;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.web.mapper.ItemDtoMapper;

@RestController
@RequestMapping("/api/haberes/core/item")
@RequiredArgsConstructor
public class ItemController {

    private static final int DEFAULT_LIMIT = 99999;

    private final ItemService service;
    private final ItemDtoMapper itemDtoMapper;

    @GetMapping("/legajo/{legajoId}/{anho}/{mes}")
    public ResponseEntity<List<ItemResponse>> findAllByLegajo(@PathVariable Long legajoId, @PathVariable Integer anho,
            @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponseList(service.getItemsByLegajo(legajoId, anho, mes)));
    }

    @GetMapping("/codigo/{codigoId}/{anho}/{mes}")
    public ResponseEntity<List<ItemResponse>> findAllByCodigoId(@PathVariable Integer codigoId,
            @PathVariable Integer anho, @PathVariable Integer mes) {
        return ResponseEntity.ok(toResponseList(service.getItemsByCodigo(codigoId, anho, mes)));
    }

    @GetMapping("/periodo/{anho}/{mes}/{limit}")
    public ResponseEntity<List<ItemResponse>> findAllByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes,
            @PathVariable Integer limit) {
        if (limit == 0) {
            limit = DEFAULT_LIMIT;
        }
        return ResponseEntity.ok(toResponseList(service.getItemsByPeriodo(anho, mes, limit)));
    }

    @GetMapping("/periodolegajo/{anho}/{mes}/{legajoId}/{limit}")
    public ResponseEntity<List<ItemResponse>> findAllByPeriodoAndLegajo(@PathVariable Integer anho,
            @PathVariable Integer mes, @PathVariable Long legajoId, @PathVariable Integer limit) {
        if (limit == 0) {
            limit = DEFAULT_LIMIT;
        }
        return ResponseEntity.ok(toResponseList(service.getItemsByPeriodoAndLegajo(anho, mes, legajoId, limit)));
    }

    @GetMapping("/unique/{legajoId}/{anho}/{mes}/{codigoId}")
    public ResponseEntity<ItemResponse> findByUnique(@PathVariable Long legajoId, @PathVariable Integer anho,
            @PathVariable Integer mes, @PathVariable Integer codigoId) {
        try {
            return ResponseEntity.ok(itemDtoMapper.toResponse(service.findByUnique(legajoId, anho, mes, codigoId)));
        } catch (ItemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<ItemResponse> add(@Valid @RequestBody ItemRequest request) {
        Item created = service.add(itemDtoMapper.toDomain(request));
        return new ResponseEntity<>(itemDtoMapper.toResponse(created), HttpStatus.CREATED);
    }

    @PutMapping("/{itemId}")
    public ResponseEntity<ItemResponse> update(@Valid @RequestBody ItemRequest request, @PathVariable Long itemId) {
        try {
            Item updated = service.update(itemDtoMapper.toDomain(request), itemId);
            return ResponseEntity.ok(itemDtoMapper.toResponse(updated));
        } catch (ItemException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @PutMapping("/")
    public ResponseEntity<List<ItemResponse>> saveAll(@Valid @RequestBody List<ItemRequest> requests) {
        List<Item> items = requests.stream()
                .map(itemDtoMapper::toDomain)
                .collect(Collectors.toList());
        return ResponseEntity.ok(toResponseList(service.saveAllItems(items)));
    }

    @DeleteMapping("/periodo/{anho}/{mes}")
    public ResponseEntity<Void> deleteByPeriodo(@PathVariable Integer anho, @PathVariable Integer mes) {
        service.deleteByPeriodo(anho, mes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/legajo/{legajoId}/{anho}/{mes}/deleteallbyzero")
    public ResponseEntity<Void> deleteAllByZero(@PathVariable Long legajoId, @PathVariable Integer anho,
            @PathVariable Integer mes) {
        service.deleteAllByZero(legajoId, anho, mes);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/onlyETEC/{legajoId}/{anho}/{mes}")
    public ResponseEntity<Boolean> onlyETEC(@PathVariable Long legajoId, @PathVariable Integer anho,
            @PathVariable Integer mes) {
        return ResponseEntity.ok(service.onlyETEC(legajoId, anho, mes));
    }

    private List<ItemResponse> toResponseList(List<Item> items) {
        return items.stream()
                .map(itemDtoMapper::toResponse)
                .collect(Collectors.toList());
    }
}
