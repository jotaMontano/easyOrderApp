package com.moonshine.service.mapper;

import com.moonshine.domain.*;
import com.moonshine.service.dto.ExtraDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Extra and its DTO ExtraDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class})
public interface ExtraMapper extends EntityMapper<ExtraDTO, Extra> {

    @Mapping(source = "client.id", target = "clientId")
    ExtraDTO toDto(Extra extra);

    @Mapping(target = "extraInLines", ignore = true)
    @Mapping(target = "products", ignore = true)
    @Mapping(source = "clientId", target = "client")
    Extra toEntity(ExtraDTO extraDTO);

    default Extra fromId(Long id) {
        if (id == null) {
            return null;
        }
        Extra extra = new Extra();
        extra.setId(id);
        return extra;
    }
}
