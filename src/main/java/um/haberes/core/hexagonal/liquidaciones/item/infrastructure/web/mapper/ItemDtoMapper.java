package um.haberes.core.hexagonal.liquidaciones.item.infrastructure.web.mapper;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import um.haberes.core.hexagonal.liquidaciones.codigo.infrastructure.web.mapper.CodigoDtoMapper;
import um.haberes.core.hexagonal.liquidaciones.item.domain.model.Item;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.web.dto.ItemRequest;
import um.haberes.core.hexagonal.liquidaciones.item.infrastructure.web.dto.ItemResponse;
import um.haberes.core.hexagonal.personas.persona.infrastructure.web.mapper.PersonaDtoMapper;

@Component
@RequiredArgsConstructor
public class ItemDtoMapper {

    private final CodigoDtoMapper codigoDtoMapper;

    private final PersonaDtoMapper personaDtoMapper;

    public Item toDomain(ItemRequest request) {
        if (request == null) {
            return null;
        }
        Item.ItemBuilder builder = Item.builder()
                .legajoId(request.getLegajoId())
                .anho(request.getAnho())
                .mes(request.getMes())
                .codigoId(request.getCodigoId())
                .importe(request.getImporte());
        if (request.getCodigoNombre() != null) {
            builder.codigoNombre(request.getCodigoNombre());
        }
        return builder.build();
    }

    public ItemResponse toResponse(Item domain) {
        if (domain == null) {
            return null;
        }
        return ItemResponse.builder()
                .itemId(domain.getItemId())
                .legajoId(domain.getLegajoId())
                .anho(domain.getAnho())
                .mes(domain.getMes())
                .codigoId(domain.getCodigoId())
                .codigoNombre(domain.getCodigoNombre())
                .importe(domain.getImporte())
                .codigo(codigoDtoMapper.toResponse(domain.getCodigo()))
                .persona(personaDtoMapper.toResponse(domain.getPersona()))
                .build();
    }
}
